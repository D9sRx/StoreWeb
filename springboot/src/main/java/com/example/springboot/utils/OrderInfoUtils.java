package com.example.springboot.utils;

import com.example.springboot.mapper.OrdersMapper;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInfoUtils {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private GoodsInfoUtils goodsInfoUtils;

    //获取单个订单的详情信息
    public OrderInfo getOrderInfo(int orderId){
        //获取该订单的基础信息
        OrderInfo orderInfo = ordersMapper.selectOrderInfoBYOrderId(orderId);


        /*获取该订单的商品信息*/
        GoodsInfo goodsInfo = goodsInfoUtils.getGoodsInfo(orderInfo.getStoreId(), orderInfo.getGoodsId(), orderInfo.getSkuId());

        if(goodsInfo != null){

            //封住商品的详情信息
            orderInfo.setGoodsInfo(goodsInfo);
        }


        return orderInfo;
    }

}



























