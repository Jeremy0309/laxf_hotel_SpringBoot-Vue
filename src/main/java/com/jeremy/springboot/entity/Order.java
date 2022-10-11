package com.jeremy.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author jeremy
 * @since 2022-02-24
 */
@Getter
@Setter
  @TableName("sys_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("订单id")
        @TableId(value = "o_id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("入住人姓名")
      @TableField("o_customer_name")
    private String customerName;


//    @DateTimeFormat来控制入参，@JsonFormat来控制出参
      @ApiModelProperty("入住时间")
      @TableField("o_checkin_time")
      @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8") //不设置默认得到的时间就是早上8点（东八区的时间），设置了就是0点
      @DateTimeFormat(pattern = "yyyy-MM-dd")
      private Date checkinTime;

      @ApiModelProperty("退房时间")
      @TableField("o_checkout_time")
      @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
      private Date checkoutTime;

      @ApiModelProperty("订单价格")
      @TableField("o_price")
    private BigDecimal price;

      @ApiModelProperty("入住房间")
      @TableField("o_room_id")
    private Integer roomId;

      @ApiModelProperty("订单状态，1=订单正常，0=已取消订单")
      @TableField("o_state")
    private Boolean state;


}
