package com.jeremy.springboot.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeremy.springboot.common.Result;
import com.jeremy.springboot.entity.UpFiles;
import com.jeremy.springboot.entity.User;
import com.jeremy.springboot.service.IUpFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    IUpFilesService upFilesService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    /**
     * 文件上传接口
     * @param file
     * @return
     * @throws IOException
     */
    //@RequestParam接收的参数是来自requestHeader中，即请求头。
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        if (!uploadFile.getParentFile().exists()){
            uploadFile.getParentFile().mkdirs();
        }
        //将上传的文件 file 存储到磁盘
        file.transferTo(uploadFile);
        //获取文件的url
        String url;
        //文件上传完后，获取文件md5
        String md5 = SecureUtil.md5(uploadFile);
        //根据md5从数据库查询是否存在相同记录
        UpFiles dbFiles = getFileByMd5(md5);
        if (dbFiles != null){
            //如果db里已经存在相同记录，则共用一个url，不再传输文件
            url = dbFiles.getUrl();
            //删除刚上传的重复内容文件
            uploadFile.delete();
        }else{
            //如果db里不存在相同记录，则url赋新值
            url ="http://localhost:9090/file/"+fileUUID;
        }

        //存储数据库
        UpFiles saveFile = new UpFiles();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);//kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        upFilesService.save(saveFile);
        return url;
    }

    /**
     * 文件下载接口
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUUID);

        //设置流的输出格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        byte[] bytes = FileUtil.readBytes(uploadFile);
        os.write(bytes);
        os.flush();
        os.close();
    }

    public UpFiles getFileByMd5(String md5){
        QueryWrapper<UpFiles> querywrapper = new QueryWrapper<>();
        querywrapper.eq("md5",md5);
        List<UpFiles> list = upFilesService.list(querywrapper);
        return list.size()==0? null:list.get(0);
    }


    /**
     * 分页查询接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){
        IPage<UpFiles> page = new Page<>(pageNum,pageSize);
        QueryWrapper<UpFiles> queryWrapper = new QueryWrapper<>();
        if(!"".equals(name)){
            queryWrapper.like("name",name);
        }
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
//        queryWrapper.and(w->w.like("address",address));
        queryWrapper.orderByDesc("id");
        return Result.success(upFilesService.page(page, queryWrapper));
    }

    /**
     * 假删除接口
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        UpFiles file = upFilesService.getById(id);
        file.setIsDelete(true);
        upFilesService.updateById(file);
        return  Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        QueryWrapper<UpFiles> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<UpFiles> list = upFilesService.list(queryWrapper);
        for (UpFiles file : list) {
            file.setIsDelete(true);
            upFilesService.updateById(file);
        }
        return Result.success();
    }
    //新增或更新
    @PostMapping("/update")
    public Result update(@RequestBody UpFiles upFile){
        return Result.success(upFilesService.saveOrUpdate(upFile));
    }




}
