package com.jeremy.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
  @TableName("sys_room")
@ApiModel(value = "Room对象", description = "")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("房间id")
        @TableId(value = "r_id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("房间名")
      @TableField("r_name")
    private String name;

      @ApiModelProperty("价格")
      @TableField("r_price")
    private BigDecimal price;

      @ApiModelProperty("状态")
      @TableField("r_state")
    private String state;

      @ApiModelProperty("位置")
      @TableField("r_position")
    private String position;

      @ApiModelProperty("描述")
      @TableField("r_descrption")
    private String descrption;


}
