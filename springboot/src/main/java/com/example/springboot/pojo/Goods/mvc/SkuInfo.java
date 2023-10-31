package com.example.springboot.pojo.Goods.mvc;

import com.example.springboot.pojo.Goods.SkuDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SkuInfo {
    private int skuId ;
    private int goodsId ;
    private int storeId ;
    private double skuInfoPrice ;
    private int status ;  //商品的上下架控制 0： 下架   1： 上架
    private int currentStorage ;   //当前库存
    private int lowestStorage ;  //最低库存
    private LocalDateTime createTime ;   //创建时间
    private LocalDateTime updateTime ;   //更新时间
    private List<SkuDetail> skuDetails;
}
