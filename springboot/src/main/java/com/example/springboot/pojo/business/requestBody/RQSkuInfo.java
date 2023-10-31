package com.example.springboot.pojo.business.requestBody;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RQSkuInfo {
    private int storeId ;
    private int goodsId ;
    private int currentStorage ;   //当前库存
    private int lowestStorage ;  //最低库存
    private HashMap<String, Double> skuDetailInfo ;
}
