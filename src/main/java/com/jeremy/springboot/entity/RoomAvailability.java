package com.jeremy.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * order_id用于退房时候根据其来删除房间占用记录		订单表，sys_order，                    主表	可用表，sys_room_availability ，从表
 * </p>
 *
 * @author jeremy
 * @since 2022-02-25
 */
@Getter
@Setter
  @TableName("sys_room_availability")
@ApiModel(value = "RoomAvailability对象", description = "order_id用于退房时候根据其来删除房间占用记录		订单表，sys_order，                    主表	可用表，sys_room_availability ，从表")
public class RoomAvailability implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("可用表id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Integer orderId;

    @ApiModelProperty("房间id")
    @TableField("room_id")
    private Integer roomId;

    @ApiModelProperty("不可用日期")
    @TableField("date_occupied")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOccupied;

      @ApiModelProperty("房间名")
      @TableField("room_name")
    private String roomName;


}
