package com.jeremy.springboot.controller.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RoomAvaVo  {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern ="yyyy-MM-dd" )
//    private Date startDate;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern ="yyyy-MM-dd" )
//    private Date endDate;

}
