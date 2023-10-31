package com.example.springboot.pojo.consumer.myOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Order {
    private int orderId ;
    private String consumerId ;
    private double orderPrice ;
    private int goodsNum ;
    private int storeId ;
    private int addressId ;
    private int goodsId ;
    private int skuId ;
    private Integer payStatus ;
    private LocalDateTime payTime ;
    private LocalDateTime createTime ;
    private Integer isDelete ;
    private String statusText ;
}
