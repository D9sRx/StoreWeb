package com.example.springboot.utils;

import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.pojo.Goods.SkuDetail;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailImageInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.Goods.mvc.SkuInfo;
import com.example.springboot.utils.components.GoodsDetailImageInfoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoodsInfoUtils {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDetailImageInfoUtils goodsDetailImageInfoUtils;

    //封装商品信息（根据商品的storeId, goodsId, skuId）
    public GoodsInfo getGoodsInfo(int storeId, int goodsId, int skuId){
        //根据storeId ,goodsId 先查询出商品的基本信息
        GoodsInfo goodsInfo = goodsMapper.selectGoodsInfoByStoreIdWithGoodsId(storeId, goodsId);

        if(goodsInfo == null){
            return null ;
        }
        /* 根据storeId,goodsId,skuId对其规格进行封装 */
        SkuInfo skuInfo = goodsMapper.selectSkuInfoByStoreIdWithSkuId(storeId, skuId);
        if(skuInfo == null){
            return null ;
        }
        List<SkuInfo> skuList = new ArrayList<>();
        skuList.add(skuInfo);
        goodsInfo.setSkuInfos(skuList);
        //封装规格详情信息
        List<SkuDetail> skuDetails = goodsMapper.selectSkuDetailBySkuId(storeId, skuId);
        skuInfo.setSkuDetails(skuDetails);
        /*封装这件商品的价格信息*/
        double goodsTotalPrice = 0;
        goodsTotalPrice+=goodsInfo.getGoodsSellPrice();
        for (SkuDetail skuDetail : skuDetails) {
            goodsTotalPrice+=skuDetail.getPrice();
        }
        goodsInfo.setGoodsInfoPrice(goodsTotalPrice);

        /* 对该商品的图片信息进行封装 */
        GoodsDetailImageInfo goodsDetailImageInfo = goodsDetailImageInfoUtils.getGoodsDetailImageInfo(storeId, goodsId);
        goodsInfo.setGoodsDetailImageInfo(goodsDetailImageInfo);

        return goodsInfo;
    }

    //获取每件商品的价格
    public double getGoodsPrice(int storeId, int goodsId, int skuId){
        //获取该商品的信息
        GoodsInfo goodsInfo = goodsMapper.selectGoodsInfoByStoreIdWithGoodsId(storeId, goodsId);
        double goodsSellPrice = goodsInfo.getGoodsSellPrice();
        //没有选规格
        if(skuId < 0){
            return goodsSellPrice;
        }
        List<SkuDetail> skuDetails = goodsMapper.selectSkuDetailBySkuId(storeId, skuId);
        for (SkuDetail skuDetail : skuDetails) {
            goodsSellPrice+=skuDetail.getPrice();
        }
        return goodsSellPrice;
    }
}

















