package com.example.springboot.service.impl;

import com.example.springboot.mapper.BusinessMapper;
import com.example.springboot.mapper.GoodsCategoryMapper;
import com.example.springboot.mapper.GoodsMapper;
import com.example.springboot.mapper.OrdersMapper;
import com.example.springboot.pojo.Goods.Goods;
import com.example.springboot.pojo.Goods.Sku;
import com.example.springboot.pojo.Goods.SkuDetail;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.Goods.mvc.SkuInfo;
import com.example.springboot.pojo.GoodsCategory.GoodsCategory;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.business.Business;
import com.example.springboot.pojo.business.requestBody.RQGoodsInfo;
import com.example.springboot.pojo.business.requestBody.RQSkuInfo;
import com.example.springboot.pojo.consumer.myOrder.Order;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import com.example.springboot.service.BusinessService;
import com.example.springboot.utils.GoodsCategoryUtils;
import com.example.springboot.utils.GoodsInfoUtils;
import com.example.springboot.utils.OrderInfoUtils;
import com.example.springboot.utils.TradeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsCategoryUtils goodsCategoryUtils;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsInfoUtils goodsInfoUtils;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderInfoUtils orderInfoUtils;

    @Autowired
    private TradeUtils tradeUtils;

/* 商家对自身的操作 */

    //注册
    public String register(String nickName, String password){
        //先查询该商家是否是已经注册过了的
        Business s_business = businessMapper.selectBusinessByNickName(nickName);
        if(s_business != null){    //已注册
            return "该手机号已经注册";
        }
        //进行注册
        Business business = new Business();
        business.setNickName(nickName);
        business.setPassword(password);

        int i = businessMapper.insertBusiness(business);
        return "注册成功";
    }

    //登录
    public String login(String nickName, String password){
        //先查询该商家是否是已经注册过了的
        Business s_business = businessMapper.selectBusinessByNickName(nickName);
        if(s_business == null){ //未注册
            return "该用户名未注册，请先注册";
        }
        //进行登录校验
        if(!s_business.getPassword().equals(password)){   //密码不对
            return "您输入的密码不对，请重新输入";
        }
        Business business = businessMapper.selectBusinessByNickName(nickName);
        return " "+business.getStoreId();
    }



/* 商家对于商品分类的操作 */

    //查询所有的商品分类情况
    public KGoodsCategory selectAllGoodsCategory() throws Exception {
        //查询最高级别的商品类别信息
        GoodsCategory goodsCategory = goodsCategoryMapper.selectCategoryByCategoryId(0);

        //封装第一层类别信息
        KGoodsCategory kGoodsCategory = new KGoodsCategory(goodsCategory.getName(),
                goodsCategory.getImageUrl(), goodsCategory.getSort(), null);

        //利用递归的思想嵌套封装下面层级的信息
        goodsCategoryUtils.setGoodsCategory(0,kGoodsCategory);

        return kGoodsCategory;
    }


/* 商家对于商品的操作 */

    //上架商品
    public String shelvesGoods(RQGoodsInfo rqGoodsInfo){
        int storeId = rqGoodsInfo.getStoreId();
        int goodsId = rqGoodsInfo.getGoodsId();
        int goodsCategory = rqGoodsInfo.getGoodsCategory();
        //判断该商品id是否已经被该商家使用过
        Goods s_goods = goodsMapper.selectGoodsByStoreIdWithGoodsId(storeId, goodsId);
        if(s_goods != null) {
            //该商品id已经使用过
            return "该商品id已经使用过，请换一个商品编码";
        }
        //进行上架商品的操作
        Goods goods = new Goods();
        goods.setGoodsCategory(goodsCategory);
        goods.setStoreId(storeId);
        goods.setGoodsId(goodsId);
        goods.setGoodsName(rqGoodsInfo.getGoodsName());
        goods.setImageUrl(rqGoodsInfo.getImageUrl());
        goods.setGoodsInitPrice(rqGoodsInfo.getGoodsInitPrice());
        goods.setGoodsSellPrice(rqGoodsInfo.getGoodsSellPrice());

        int currentStorage = 0;
        int lowestStorage = 0;
        List<RQSkuInfo> rqSkuInfos = rqGoodsInfo.getRqSkuInfos();

        if (rqSkuInfos==null || rqSkuInfos.size()==0){
            return "规格信息不能为空";
        }

        for (RQSkuInfo rqSkuInfo : rqSkuInfos) {
            currentStorage += rqSkuInfo.getCurrentStorage();
            lowestStorage += rqSkuInfo.getLowestStorage();
        }
        goods.setCurrentStorage(currentStorage);
        goods.setLowestStorage(lowestStorage);

        //上架
        int i = goodsMapper.insertGoods(goods);

        //添加商品规格
        for (RQSkuInfo rqSkuInfo : rqSkuInfos) {
            String s = addSku(rqSkuInfo);
        }
        return "上架商品成功";
    }


    //下架商品
    public String offGoods(int storeId, int goodsId){
        Goods goods = goodsMapper.selectGoodsByStoreIdWithGoodsId(storeId, goodsId);
        goods.setStatus(0);
        int i = goodsMapper.delGoods(goodsId);
        return "下架商品"+goods.getGoodsName()+"成功";
    }


    //查询该商家上架的商品信息（ 分页查询 ）
    public List<GoodsInfo> selectShelvesGoodsInfo(int storeId, int page, int pageSize, String goodsName){
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        //查询该商家所有上架的商品基础信息
        List<GoodsInfo> goodsList = goodsMapper.selectGoodsInfoByStoreId(storeId, goodsName);
        if(goodsList.size() == 0){
            return null ;
        }
        for (GoodsInfo goodsinfo : goodsList) {
            //获取该商品的规格信息
            List<SkuInfo> skuInfos = goodsMapper.selectSkuInfoByStoreIdWithGoodsId(storeId, goodsinfo.getGoodsId());
            if(skuInfos.size() == 0){
                goodsInfos.add(goodsinfo);
                continue ;
            }
            for (SkuInfo skuInfo : skuInfos) {
                //获取该商品的所有信息
                GoodsInfo goodsInfo = goodsInfoUtils.getGoodsInfo(storeId, goodsinfo.getGoodsId(), skuInfo.getSkuId());
                goodsInfos.add(goodsInfo);
            }
        }
        return goodsInfos;
    }


    //根据id查询该商品的信息


/* 商家对于sku表的操作 */

    //给某件商品添加规格
    public String addSku(RQSkuInfo rqSkuInfo){
        int storeId = rqSkuInfo.getStoreId();
        int goodsId = rqSkuInfo.getGoodsId();
        //查询出该商品的基础信息
        Goods goods = goodsMapper.selectGoodsByStoreIdWithGoodsId(storeId, goodsId);
        if(goods == null){
            return "该商品不存在，无法添加规格信息";
        }
        //添加规格信息
        Sku sku = new Sku();
        sku.setStoreId(storeId);
        sku.setGoodsId(goodsId);
        sku.setCurrentStorage(rqSkuInfo.getCurrentStorage());
        sku.setLowestStorage(rqSkuInfo.getLowestStorage());
        int sku_id = goodsMapper.insertSku(sku);

        //添加规格详情信息
        SkuDetail skuDetail = new SkuDetail();
        skuDetail.setSkuInfoId(sku_id);
        skuDetail.setStoreId(storeId);
        HashMap<String, Double> skuDetailInfo = rqSkuInfo.getSkuDetailInfo();
        for (Map.Entry<String, Double> stringDoubleEntry : skuDetailInfo.entrySet()) {
            skuDetail.setName(stringDoubleEntry.getKey());
            skuDetail.setPrice(stringDoubleEntry.getValue());
            goodsMapper.insertSkuDetail(skuDetail);
        }
        return "添加规格成功";
    }

    //删除某商品的某sku规格
    public String delSku(int storeId, int goodsId, int skuId){
        int i = goodsMapper.delSku(storeId, skuId);
        return "删除规格成功";
    }



/* 商家对订单表进行操作 */

    //查询该商家要处理的订单（条件分页查询）
    public List<OrderInfo> selectOrderInfo(int storeId, int page, int pageSize, String statusText,
                                           LocalDateTime createTimeStart, LocalDateTime createTimeEnd){

        List<OrderInfo> orderInfos = new ArrayList<>();
        //查询出基本的订单信息（根据storeId）
        List<Integer> orderIds = ordersMapper.selectOrderIdsByPagination(storeId, (page - 1)*pageSize, pageSize, statusText, createTimeStart, createTimeEnd);
        for (Integer orderId : orderIds) {
            OrderInfo orderInfo = orderInfoUtils.getOrderInfo(orderId);
            orderInfos.add(orderInfo);
        }
        return orderInfos;
    }

    //修改订单的发货状态
    public String modifyStatusText(int orderId, String statusText){
        Order order = ordersMapper.selectOrderByOrderId(orderId);
        order.setStatusText(statusText);
        int i = ordersMapper.updateOrder(order);
        return "订单状态修改成功";
    }

    //审批消费者的申请退款
    public String approveRefund(int orderId){
        //查询该订单的信息
        Order order = ordersMapper.selectOrderByOrderId(orderId);
        String msg = tradeUtils.businessSendMoneyToConsumer(order.getStoreId(), order.getOrderPrice(), order.getConsumerId());
        if("商家余额不足，请联系商家退款".equals(msg)){
            return "您的余额不足，无法给该订单进行退款";
        }
        order.setStatusText("已退款");
        return "退款操作成功";
    }

    @Override
    public String removeOrder(int orderId) {
        int i = businessMapper.deleteOrder(orderId);
        if(i == 0){
            return "删除失败";
        }
        return "删除成功";

    }

    @Override
    public String sendOrder(int orderId) {
        int i = businessMapper.sendOrder(orderId);
        if(i == 0){
            return "发货失败";
        }
        return "发货成功";

    }

    public List<GoodsInfo> selectShelvesGoodsInfoBlur(int storeId, int page, int pageSize,String goodsName){
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        //查询该商家所有上架的商品基础信息
        List<GoodsInfo> goodsList = goodsMapper.selectGoodsInfoByStoreIdWithBlurName(storeId,goodsName);
        if(goodsList.size() == 0){
            return null ;
        }
        for (GoodsInfo goodsinfo : goodsList) {
            //获取该商品的规格信息
            List<SkuInfo> skuInfos = goodsMapper.selectSkuInfoByStoreIdWithGoodsId(storeId, goodsinfo.getGoodsId());
            if(skuInfos.size() == 0){
                goodsInfos.add(goodsinfo);
                continue ;
            }
            for (SkuInfo skuInfo : skuInfos) {
                //获取该商品的所有信息
                GoodsInfo goodsInfo = goodsInfoUtils.getGoodsInfo(storeId, goodsinfo.getGoodsId(), skuInfo.getSkuId());
                goodsInfos.add(goodsInfo);
            }
        }
        return goodsInfos;
    }



}













