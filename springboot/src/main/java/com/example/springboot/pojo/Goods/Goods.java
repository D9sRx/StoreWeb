package com.example.springboot.pojo.Goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Goods {
    private int goodsId ;   //商品的编号
    private String goodsName ;   //商品的名字
    private double goodsInitPrice ;  //商品的成本
    private double goodsSellPrice ;  //商品的出售价格
    private int goodsCategory ;  //商品的类别
    private int storeId ;  //商品的厂商
    private int currentStorage ;   //当前库存
    private int lowestStorage ;  //最低库存
    private int status ;  //商品的上下架控制 0： 下架   1： 上架
    private LocalDateTime createTime ;   //创建时间
    private LocalDateTime updateTime ;   //更新时间
    private String imageUrl ;  //保存的照片的url
}
