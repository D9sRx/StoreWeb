package com.example.springboot.pojo.business.requestBody;

import cn.hutool.core.collection.CollUtil;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailImageInfo;
import com.example.springboot.pojo.Goods.mvc.SkuInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RQGoodsInfo {
    private int goodsId ;   //商品的编号
    private String goodsName ;   //商品的名字
    private double goodsInitPrice ;  //商品的成本
    private double goodsSellPrice ;  //商品的出售价格
    private int goodsCategory ;  //商品的类别
    private int storeId ;  //商品的厂商
    private String imageUrl ;  //保存的照片的url
    private GoodsDetailImageInfo goodsDetailImageInfo; //照片
    private List<RQSkuInfo> rqSkuInfos ;
}
