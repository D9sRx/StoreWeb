package com.example.springboot.controller;

import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.business.requestBody.RQGoodsInfo;
import com.example.springboot.pojo.business.requestBody.RQSkuInfo;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import com.example.springboot.service.BusinessService;
import com.example.springboot.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/business")
@CrossOrigin        /*解决跨域问题*/
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @GetMapping("/register")
    //注册
    public Result register(String nickName, String password){
        String msg = businessService.register(nickName, password);
        if("注册成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @GetMapping("/login")
    //登录
    public Result login(String nickName, String password){
        String msg = businessService.login(nickName, password);
        if("该手机号未注册，请先注册".equals(msg) || "您输入的密码不对，请重新输入".equals(msg)){
            return Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/selectAllGoodsCategory")
    //查询所有的商品分类情况
    public Result selectAllGoodsCategory() throws Exception {
        KGoodsCategory kGoodsCategory = businessService.selectAllGoodsCategory();
        return Result.success(kGoodsCategory);
    }

/* 商家对于商品的操作 */

    @PostMapping("/shelvesGoods")
    //上架商品
    public Result shelvesGoods(@RequestBody RQGoodsInfo rqGoodsInfo){

        String msg = businessService.shelvesGoods(rqGoodsInfo);
        if("上架商品成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @DeleteMapping("/offGoods")
    //下架商品

    public Result offGoods(int storeId, int goodsId){

        String msg = businessService.offGoods(storeId, goodsId);
        return Result.success(msg);
    }

    @GetMapping("/selectShelvesGoodsInfo")
    //查询所有的商品信息
    public Result selectShelvesGoodsInfo(int storeId
            ,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize, String goodsName){

        List<GoodsInfo> goodsInfos = businessService.selectShelvesGoodsInfo(storeId, page, pageSize, goodsName);
        return Result.success(goodsInfos);

    }


    //根据id查询该商品的信息


/* 商家对于sku表的操作 */

    @PostMapping("/addSku")
    //给某件商品添加规格
    public Result addSku(RQSkuInfo rqSkuInfo){

        String msg = businessService.addSku(rqSkuInfo);
        if("添加规格成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @DeleteMapping("/delSku")
    //删除某商品的某sku规格
    public Result delSku(int storeId, int goodsId, int skuId){

        String msg = businessService.delSku(storeId, goodsId, skuId);
        if("删除规格成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

/* 商家对订单表进行操作 */

    @GetMapping("/selectOrderInfo")
    //查询该商家要处理的订单（条件分页查询）
    public Result selectOrderInfo(int storeId, @RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize, String statusText,
                                  LocalDateTime createTimeStart, LocalDateTime createTimeEnd){

        List<OrderInfo> orderInfos = businessService.selectOrderInfo(storeId, page, pageSize, statusText, createTimeStart, createTimeEnd);

        return Result.success(orderInfos);
    }

    @PutMapping("/modifyStatusText")
    //修改订单的发货状态
    public Result modifyStatusText(int orderId, String statusText){
        String msg = businessService.modifyStatusText(orderId, statusText);
        return Result.success(msg);

    }

    @PutMapping("/approveRefund")
    //审批消费者的申请退款
    public Result approveRefund(int orderId){
        String msg = businessService.approveRefund(orderId);
        if("退款操作成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @GetMapping("/removeOrder")
    public Result removeOrder(int orderId){
        String msg = businessService.removeOrder(orderId);
        if("删除失败".equals(msg)){
            return  Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/sendOrder")
    public Result sendOrder(int orderId){
        String msg = businessService.sendOrder(orderId);
        if("发货失败".equals(msg)){
            return  Result.error(msg);
        }
        return Result.success(msg);
    }

    @GetMapping("/selectShelvesGoodsInfoBlur")
    //模糊查询
    public Result selectShelvesGoodsInfoBlur(int storeId
            ,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int pageSize,String goodsName){

        List<GoodsInfo> goodsInfos = businessService.selectShelvesGoodsInfoBlur(storeId, page, pageSize,goodsName);
        return Result.success(goodsInfos);

    }

}























