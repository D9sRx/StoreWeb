package com.example.springboot.pojo.Goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Sku {
    private int skuId ;  //规格id
    private int goodsId ; //商品id
    private int storeId ; //商家id
    private int status ;  //商品的上下架控制 0： 下架   1： 上架
    private int currentStorage ;   //当前库存
    private int lowestStorage ;  //最低库存
    private LocalDateTime createTime ;   //创建时间
    private LocalDateTime updateTime ;   //更新时间
}
