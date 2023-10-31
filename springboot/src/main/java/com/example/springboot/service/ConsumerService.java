package com.example.springboot.service;

import com.example.springboot.pojo.Goods.Goods;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.consumer.Address;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;

import java.util.List;

public interface ConsumerService {

/* 顾客对自己的操作 */

    //注册
    public String register(String mobile, String password);

    //登录
    public String login(String mobile, String password);


    /* 顾客对余额的查询*/
    public Double selectMyBalance(String nickName);

/* 顾客对商品分类的查询*/

    //查询所有的商品分类信息
    public KGoodsCategory selectAllGoodsCategory() throws Exception;


/* 顾客对商品的操作 */

    //查询某分类的所有商品信息（根据商品分类id来查询）
    public List<GoodsInfo> selectGoodsInfoByGoodsCategoryId(int categoryId);

    //根据商品名称来模糊查询商品基础信息
    public List<Goods> selectGoodsByGoodsName(String goodsName);

    //查看商品详情页
    public GoodsDetailInfo selectGoodsDetail(int storeId, int goodsId);


/* 顾客对订单的管理 */

    //加入订单
    public String addOrder(String consumerId, int storeId,int goodsId, int skuId, int goodsNum, int payStatus);

    //查看所有的订单信息（根据status_text进行分类）
    public List<OrderInfo> selectMyOrders(String consumerId);

    //删除某订单信息
    public String delOrderByOne(String consumerId, int orderId);

    //申请退款（向商家去申请：如果是待发货状态的话，申请退款无需通过商家，直接退款，反之则否）
    public String requestRefund(String consumerId, int orderId);

    //订单付款
    public String payOrderMoney(String consumerId, int orderId);


/*对地址的操作*/

    //设置默认收货地址
    public String setDefaultAddress(String consumerId, int addressId);

    //查看默认收获地址
    public Address selectDefaultAddress(String consumerId);

    //查看消费者的所有收货地址信息

    //添加收货地址（控制添加的收获地址数最多为5条）
    public String insertAddress(String consumerId, String name, String phone, String province,
                                String city, String region, String detail);

    //更新收获地址
    public String updateAddress(Address address);

    //删除收货地址
    public String delAddressByAddressId(String consumerId, int addressId);

    String addConsumerOrder(String consumerId, Double goodsSellPrice, int goodsId, int goodsNum, int storeId);

    int selectStoreIdByGoodsId(int goodsId);

    public String updateBalance(String consumerId,Double balance);

    public String addBalance(String consumerId,Double addBalance);

    String addBusinessBalance(Double goodsSellPrice, int goodsNum, int storeId);

    public  Double selectBusinessBalance(int storeId);
}
