package com.jeremy.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_file")
public class UpFiles {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String type;
    private Long size;
    private String url;
    private Boolean isDelete;
    private Boolean enable;
    private String md5;
}
