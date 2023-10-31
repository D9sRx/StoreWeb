package com.example.springboot.controller;

import com.example.springboot.pojo.Goods.Goods;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import com.example.springboot.service.ConsumerService;
import com.example.springboot.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/consumer")
@CrossOrigin        /*解决跨域问题*/
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/register")
    //注册
    public Result register(String nickName, String password){
        String msg = consumerService.register(nickName, password);
        if("注册成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @GetMapping("/login")
    //登录
    public Result login(String mobile, String password){
        String msg = consumerService.login(mobile, password);
        if("该手机号未注册，请先注册".equals(msg) || "您输入的密码不对，请重新输入".equals(msg)){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/selectAllGoodsCategory")
    //查询所有的商品分类情况
    public Result selectAllGoodsCategory() throws Exception {
        KGoodsCategory kGoodsCategory = consumerService.selectAllGoodsCategory();
        return Result.success(kGoodsCategory);
    }


    @GetMapping("/selectGoodsInfoByGoodsCategoryId")
    //查询某个分类的所有商品信息（根据商品分类id来查询）
    public Result selectGoodsInfoByGoodsCategoryId(int categoryId){
        List<GoodsInfo> goodsInfos = consumerService.selectGoodsInfoByGoodsCategoryId(categoryId);
        return Result.success(goodsInfos);
    }

    @GetMapping("/selectGoodsDetail")
    //查看商品详情页（根据商家和商品id来查）
    public Result selectGoodsDetail(int storeId, int goodsId){
        GoodsDetailInfo goodsDetailInfo = consumerService.selectGoodsDetail(storeId, goodsId);
        return Result.success(goodsDetailInfo);
    }


    @GetMapping("/selectGoodsByGoodsName")
    //根据商品名称来模糊查询商品基础信息
    public Result selectGoodsByGoodsName(String goodsName){
        List<Goods> goods = consumerService.selectGoodsByGoodsName(goodsName);
        return Result.success(goods);
    }

    /* 对订单的操作 */

    @GetMapping("/addOrder")
    //加入订单
    public Result addOrder(String consumerId, int storeId, int goodsId, int skuId, int goodsNum, int payStatus){
        String msg = consumerService.addOrder(consumerId, storeId,goodsId, skuId, goodsNum, payStatus);
        if("余额不足，付款失败".equals(msg)){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/addConsumerOrders")
    public Result addConsumerOrder(String consumerId, Double goodsSellPrice,int goodsId, int goodsNum, int storeId){

        String msg = consumerService.addConsumerOrder(consumerId,goodsSellPrice,goodsId,goodsNum,storeId);
        if(msg == "余额不足"){
            return  Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/selectMyOrders")
    //查看所有的订单信息（根据status_text进行分类）
    public Result selectMyOrders(String consumerId){
        List<OrderInfo> orderInfos = consumerService.selectMyOrders(consumerId);
        return Result.success(orderInfos);
    }

    @GetMapping("/delOrderByOne")
    //删除某订单信息
    public Result delOrderByOne(String consumerId, int orderId){
        String msg = consumerService.delOrderByOne(consumerId, orderId);
        if("该订单不可删除,删除失败".equals(msg)){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/requestRefund")
    //申请退款（向商家去申请：如果是待发货状态的话，申请退款无需通过商家，直接退款，反之则否）
    public Result requestRefund(String consumerId, int orderId){
        String msg = consumerService.requestRefund(consumerId, orderId);
        if("商家余额不足，请联系商家退款".equals(msg)){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/payOrderMoney")
    //订单付款
    public Result payOrderMoney(String consumerId, int orderId){
        String msg = consumerService.payOrderMoney(consumerId, orderId);
        if(msg.contains("失败")){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/selectMyBalance")
    public Result selectMyBalance(String nickName){
        Double balance = consumerService.selectMyBalance(nickName);
        if (balance == null) {
            // 处理null的情况。也许返回一个默认值或错误消息。
            return Result.error("找不到给定昵称的余额。");
        }
        return Result.success(String.valueOf(balance));
    }

    @GetMapping("/selectStoreIdByGoodsId")
    public Result selectStoreIdByGoodsId(int goodsId){
        int storeId = consumerService.selectStoreIdByGoodsId(goodsId);
        return Result.success(storeId);
    }

    @GetMapping("/addBalance")
    public Result addBalance(String consumerId,Double balance){
        String msg = consumerService.addBalance(consumerId,balance);
        if(msg == "金额不合适"){
            return Result.error(msg);
        }else{
            return Result.success(msg);
        }
    }

    @GetMapping("/addBusinessBalance")
    public Result addBusinessBalance(Double goodsSellPrice,int goodsNum,int storeId){
        String msg = consumerService.addBusinessBalance(goodsSellPrice,goodsNum,storeId);
        return Result.success(msg);
    }

    @GetMapping("/selectBusinessBalance")
    public Result selectBusinessBalance(int storeId){
        Double msg = consumerService.selectBusinessBalance(storeId);
        return Result.success(msg);
    }
}