package com.jeremy.springboot.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * mp代码生成器
 * @since 2022-02-15
 */
public class CodeGenerator {

    public static void main(String[] args) {
        generate();
    }

    private static void generate() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/laxf_hotel?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai",
                "root", "123456")
                .globalConfig(builder -> {
                    builder.author("jeremy") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                                                                //==================== 要修改 ====================
                            .outputDir("D:\\Code\\JAVA项目\\2022VUE\\laxf_hotel\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.jeremy.springboot") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                                                                //==================== 要修改 ====================
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D:\\Code\\JAVA项目\\2022VUE\\laxf_hotel\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok()
                                            .enableTableFieldAnnotation();//开启生成实体时生成字段注解
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器,否则默认@Controller
                                                                //==================== 要修改 ====================
                    builder.addInclude("sys_room_availability") // 设置需要生成的表名
                            .addFieldPrefix("o_","c_","r_")   // 设置过滤字段前缀
                            .addTablePrefix("t_", "sys_"); // 设置过滤表前缀

                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}