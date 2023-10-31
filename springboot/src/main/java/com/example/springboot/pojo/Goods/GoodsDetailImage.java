package com.example.springboot.pojo.Goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class GoodsDetailImage {
    private int id;
    private int type;  //图片的类型 （1： swiper  2:  static）
    private int storeId ; //哪个商家的
    private int goods_id ; //哪个商品的
    private String imageUrl;
}
