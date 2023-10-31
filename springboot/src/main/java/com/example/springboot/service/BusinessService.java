package com.example.springboot.service;

import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.business.requestBody.RQGoodsInfo;
import com.example.springboot.pojo.business.requestBody.RQSkuInfo;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface BusinessService {

/* 商家对自身的操作 */

    //注册
    public String register(String nickName, String password);

    //登录
    public String login(String nickName, String password);



/* 商家对于商品分类的操作 */

    //查询所有的商品分类情况
    public KGoodsCategory selectAllGoodsCategory() throws Exception;


/* 商家对于商品的操作 */

    //上架商品
    public String shelvesGoods(RQGoodsInfo rqGoodsInfo);

    //下架商品
    public String offGoods(int storeId, int goodsId);

    //查询所有的商品信息
    public List<GoodsInfo> selectShelvesGoodsInfo(int storeId, int page, int pageSize, String goodsName);


    //根据id查询该商品的信息


/* 商家对于sku表的操作 */

    //给某件商品添加规格
    public String addSku(RQSkuInfo rqSkuInfo);

    //删除某商品的某sku规格
    public String delSku(int storeId, int goodsId, int skuId);


/* 商家对订单表进行操作 */

    //查询该商家要处理的所有订单
    public List<OrderInfo> selectOrderInfo(int storeId, int page, int pageSize, String statusText, LocalDateTime createTimeStart, LocalDateTime createTimeEnd);

    //修改订单的发货状态
    public String modifyStatusText(int orderId, String statusText);

    //审批消费者的申请退款
    public String approveRefund(int orderId);


    String removeOrder(int orderId);

    String sendOrder(int orderId);

    List<GoodsInfo> selectShelvesGoodsInfoBlur(int storeId, int page, int pageSize,String goodsName);
}
