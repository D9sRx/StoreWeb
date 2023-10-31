package com.example.springboot.pojo.Goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SkuDetail {
    private int skuInfoId ;  //规格详情id
    private int storeId ;   //商家id
    private String name ;    //规格详情名称
    private double price ;   //该规格详情的价格
}
