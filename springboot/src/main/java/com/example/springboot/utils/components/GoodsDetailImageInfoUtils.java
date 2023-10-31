package com.example.springboot.utils.components;

import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.pojo.Goods.GoodsDetailImage;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsDetailImageInfoUtils {

    @Autowired
    private GoodsMapper goodsMapper;

    //获取某个商品的图片详情信息
    public GoodsDetailImageInfo getGoodsDetailImageInfo(int storeId, int goodsId){
        GoodsDetailImageInfo goodsDetailImageInfo = new GoodsDetailImageInfo();
        //轮播图
        List<GoodsDetailImage> swiperImage = goodsMapper.selectGoodsDetailImageByGoodsIdWithType(storeId, goodsId, 0);
        //静态图片
        List<GoodsDetailImage> staticImage = goodsMapper.selectGoodsDetailImageByGoodsIdWithType(storeId, goodsId, 1);

        //封装数据
        goodsDetailImageInfo.setSwiperImage(swiperImage);
        goodsDetailImageInfo.setStaticImage(staticImage);

        return goodsDetailImageInfo;
    }

}




















