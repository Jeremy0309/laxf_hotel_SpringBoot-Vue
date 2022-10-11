package com.jeremy.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeremy
 * @since 2022-02-23
 */
@Getter
@Setter
  @TableName("sys_customer")
@ApiModel(value = "Customer对象", description = "")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("客户id")
        @TableId(value = "c_id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("名称")
      @TableField("c_name")
    private String name;

      @ApiModelProperty("证件号")
      @TableField("c_credentials")
    private String credentials;

      @ApiModelProperty("性别")
      @TableField("c_gender")
    private Integer gender;

      @ApiModelProperty("类型")
      @TableField("c_type")
    private String type;

      @ApiModelProperty("联系方式")
      @TableField("c_phone")
    private String phone;

      @ApiModelProperty("创建时间")
      @TableField("c_create_time")
    private LocalDateTime createTime;


}
