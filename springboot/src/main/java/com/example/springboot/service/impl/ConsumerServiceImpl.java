package com.example.springboot.service.impl;

import com.example.springboot.mapper.*;
import com.example.springboot.pojo.Goods.Goods;
import com.example.springboot.pojo.Goods.GoodsDetailImage;
import com.example.springboot.pojo.Goods.SkuDetail;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailImageInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsDetailInfo;
import com.example.springboot.pojo.Goods.mvc.GoodsInfo;
import com.example.springboot.pojo.Goods.mvc.SkuInfo;
import com.example.springboot.pojo.GoodsCategory.GoodsCategory;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.business.Business;
import com.example.springboot.pojo.consumer.Address;
import com.example.springboot.pojo.consumer.Consumer;
import com.example.springboot.pojo.consumer.myOrder.Order;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import com.example.springboot.service.ConsumerService;
import com.example.springboot.utils.GoodsCategoryUtils;
import com.example.springboot.utils.GoodsInfoUtils;
import com.example.springboot.utils.OrderInfoUtils;
import com.example.springboot.utils.TradeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsCategoryUtils goodsCategoryUtils;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private GoodsInfoUtils goodsInfoUtils;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private OrderInfoUtils orderInfoUtils;

    @Autowired
    private TradeUtils tradeUtils;

    @Autowired
    private AddressMapper addressMapper;



/* 顾客对自己的操作 */

    //注册
    public String register(String nickName, String password){
        //先查询该消费者是否是已经注册过了的
        Consumer s_consumer = consumerMapper.selectConsumerByNickName(nickName);
        if(s_consumer != null){    //已注册
            return "该用户已经注册";
        }
        //进行注册
        Consumer consumer = new Consumer();
        UUID uuid = UUID.randomUUID();
        consumer.setConsumerId(String.valueOf(uuid));
        consumer.setNickName(nickName);
        consumer.setPassword(password);

        int i = consumerMapper.insertConsumer(consumer);
        return "注册成功";
    }

    //登录
    public String login(String mobile, String password){
        //先查询该消费者是否是已经注册过了的
        Consumer s_consumer = consumerMapper.selectConsumerByMobile(mobile);
        if(s_consumer == null){ //未注册
            return "该账号未注册，请先注册";
        }
        //进行登录校验
        if(!s_consumer.getPassword().equals(password)){   //密码不对
            return "您输入的密码不对，请重新输入";
        }
        //进行登录
        return " "+s_consumer.getConsumerId();
    }

/* 顾客对商品分类的查询*/

    //查询所有的商品分类信息
    @Override
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


/* 顾客对商品的操作 */

    //查询某个分类的所有商品信息（根据商品分类id来查询）
    public List<GoodsInfo> selectGoodsInfoByGoodsCategoryId(int categoryId){

        //根据分类id查询goods表对应的商品信息
        List<GoodsInfo> goodsList = goodsMapper.selectGoodsByCategoryId(categoryId);

        if(goodsList.size()==0){
            return null ;
        }

        /* 对每一个商品进行封装 */
        for (GoodsInfo goods : goodsList) {

            //根据（商品id,商家id）查询对应的底层东西，然后进行封装
            List<SkuInfo> skus = goodsMapper.selectSkuInfoByStoreIdWithGoodsId(goods.getStoreId(), goods.getGoodsId() );
            goods.setSkuInfos(skus);

            //根据规格的id查询对应的底层东西，然后封装
            for (SkuInfo sku : skus) {
                List<SkuDetail> skuDetails = goodsMapper.selectSkuDetailBySkuId(sku.getStoreId(),sku.getSkuId());
                sku.setSkuDetails(skuDetails);
            }
        }
        return goodsList;
    }

    //顾客查询余额

    public Double selectMyBalance(String nickName) {
        double i = consumerMapper.selectConsumerBalance(nickName);
        return i;
    }

    //根据名字进行模糊查询商品的信息
    public List<Goods> selectGoodsByGoodsName(String goodsName){
        List<Goods> goods = goodsMapper.selectGoodsByGoodsName(goodsName);
        return goods ;
    }


    //查看商品详情页（根据商家和商品id来查）
    public GoodsDetailInfo selectGoodsDetail(int storeId, int goodsId){
        //查看商品详情页（根据商家和商品id来查）
        GoodsDetailInfo goodsDetailInfo = goodsMapper.selectGoodsDetailInfoByStoreIdWithGoodsId(storeId, goodsId);

        if(goodsDetailInfo ==null){
            return  null;
        }

        /* 对该商品进行封装 */

        //根据（商品id,商家id）查询对应的底层东西，然后进行封装
        List<SkuInfo> skus = goodsMapper.selectSkuInfoByStoreIdWithGoodsId(goodsDetailInfo.getStoreId(), goodsDetailInfo.getGoodsId() );
        goodsDetailInfo.setSkuInfos(skus);

        //根据规格的id查询对应的底层东西，然后封装
        for (SkuInfo sku : skus) {
            List<SkuDetail> skuDetails = goodsMapper.selectSkuDetailBySkuId(sku.getStoreId(),sku.getSkuId());
            sku.setSkuDetails(skuDetails);
        }

        /*对图片进行封装*/
        List<GoodsDetailImage> swiperImage = goodsMapper.selectGoodsDetailImageByGoodsIdWithType(storeId, goodsId, 1);

        List<GoodsDetailImage> staticImage = goodsMapper.selectGoodsDetailImageByGoodsIdWithType(storeId, goodsId, 2);

        //设置图片
        GoodsDetailImageInfo goodsDetailImageInfo = new GoodsDetailImageInfo();
        goodsDetailImageInfo.setSwiperImage(swiperImage);
        goodsDetailImageInfo.setStaticImage(staticImage);

        //封装图片
        goodsDetailInfo.setGoodsDetailImageInfo(goodsDetailImageInfo);

        return goodsDetailInfo;
    }


/* 顾客对订单的管理 */

    //加入订单
    public String addOrder(String consumerId, int storeId, int goodsId, int skuId, int goodsNum, int payStatus) {
        //新建一个订单
        Order order = new Order();
        order.setConsumerId(consumerId);
        order.setCreateTime(LocalDateTime.now());
        order.setStoreId(storeId);
        order.setGoodsId(goodsId);
        order.setSkuId(skuId);
        order.setGoodsNum(goodsNum);
        order.setStatusText("待发货");
        order.setIsDelete(0);
        //查看这件商品的价格
        double goodsPrice = goodsInfoUtils.getGoodsPrice(storeId, goodsId, skuId);
        String msg = tradeUtils.consumerSendMoneyToBusiness(consumerId, goodsPrice, storeId);
        order.setOrderPrice(goodsPrice*goodsNum);
        //加入订单时，是否直接支付
        if(payStatus==1){
            //消费者对商家进行付款操作
            if(msg.contains("成功")){
                order.setPayStatus(1);
                order.setPayTime(LocalDateTime.now());
                ordersMapper.insertOrder(order);
                return msg;
            }
            return msg;
        }
        order.setPayStatus(0);
        int i = ordersMapper.insertOrder(order);
        return "订单已提交，请及时付款";
    }

    //查看所有的订单信息（根据status_text进行分类）
    public List<OrderInfo> selectMyOrders(String consumerId){
        List<OrderInfo> orderInfos = new ArrayList<>();
        //查询该顾客的所有订单
        List<Order> orders = ordersMapper.selectOrderByConsumerId(consumerId);
        for (Order order : orders) {
            orderInfos.add(orderInfoUtils.getOrderInfo(order.getOrderId()));
        }
        return orderInfos;
    }

    //删除某订单信息
    public String delOrderByOne(String consumerId, int orderId){
        //查询该订单的状态
        Order order = ordersMapper.selectOrderByOrderId(orderId);
        if(order.getStatusText().equals("已签收")){
            int i = ordersMapper.delOrder(orderId);
            return "删除订单成功";
        }else {
            return "该订单不可删除,删除失败";
        }
    }

    //申请退款（向商家去申请：如果是待发货状态的话，申请退款无需通过商家，直接退款，反之则否）
    public String requestRefund(String consumerId, int orderId){
        //获取该消费者的信息
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);
        //获取该订单的信息
        OrderInfo orderInfo = orderInfoUtils.getOrderInfo(orderId);
        double orderPrice = orderInfo.getOrderPrice();
        Order order = ordersMapper.selectOrderByOrderId(orderId);
        //得到该订单的支付信息
        Integer payStatus = orderInfo.getPayStatus();
        //得到该订单的发货状态
        String statusText = orderInfo.getStatusText();

        //获取商家的信息
        Business business = businessMapper.selectBusinessByStoreId(orderInfo.getStoreId());

        /* 退款规则 */
        if(payStatus.equals("待发货") && payStatus == 1){
            /* 给你进行退款 */
            String msg = tradeUtils.businessSendMoneyToConsumer(orderInfo.getStoreId(), orderPrice, consumerId);
            if(!"商家余额不足，请联系商家退款".equals(msg)){
                //销毁订单
                order.setStatusText("已退款");
                int i = ordersMapper.updateOrder(order);
            }
            return msg;
        } else if (payStatus.equals("待发货") && payStatus == 0) {
            //直接销毁订单即可
            order.setStatusText("已退款");
            ordersMapper.updateOrder(order);
            return LocalDateTime.now()+"  ----  手机号为："+business.getMobile()+"的商家向您退款了0元";
        }else{
            //需要等待商家进行审核后进行退款（发起申请提示商家）
            order.setStatusText("申请退款");
            ordersMapper.updateOrder(order);
            return "申请已发送，请等待商家的审批";
        }
    }

    //订单付款
    public String payOrderMoney(String consumerId, int orderId){
        //查询该订单的信息
        Order order = ordersMapper.selectOrderByOrderId(orderId);
        //付款
        String msg = tradeUtils.consumerSendMoneyToBusiness(consumerId, order.getOrderPrice(), order.getStoreId());
        if(msg.contains("失败")){
            return msg;
        }
        order.setPayStatus(1);
        order.setPayTime(LocalDateTime.now());
        int i = ordersMapper.updateOrder(order);
        return msg;
    }


/*对地址的操作*/
    //设置默认收货地址
    public String setDefaultAddress(String consumerId, int addressId){
        //查询该用户的数据
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);

        //更新该用户的默认收货地址
        consumer.setAddressId(addressId);
        consumerMapper.updateConsumer(consumer);

        return "更新默认地址成功";


    }

    //查看默认收获地址
    public Address selectDefaultAddress(String consumerId){

        //获取该用户的信息
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);
        int addressId = consumer.getAddressId();

        //判断该用户是否设置了默认收获地址
        if(addressId == -1){
            return null;
        }

        //获取默认收货地址
        Address address = addressMapper.selectAddressByAddressId(addressId);

        return address;

    }

    //查看消费者的所有收货地址信息
    public List<Address> selectAllAddress(String consumerId){

        //查询用户的信息
        Consumer consumer = consumerMapper.selectConsumerByConsumerId(consumerId);

        //查询地址信息
        List<Address> addressList = addressMapper.selectAllAddress();

        return addressList;

    }

    //添加收货地址（控制添加的收获地址数最多为5条）
    public String insertAddress(String consumerId, String name, String phone, String province,
                                String city, String region, String detail){

        //查询该用户添加的收货地址记录数
        int addressCount = addressMapper.addressCount(consumerId);
        if(addressCount >=5){
            return "已达到添加收货地址的上限";
        }
        //添加收获地址
        Address address = new Address();
        address.setName(name);
        address.setPhone(phone);
        address.setProvince(province);
        address.setCity(city);
        address.setRegion(region);
        address.setDetail(detail);
        address.setConsumerId(consumerId);

        int i = addressMapper.insertAddress(address);

        return "添加收货地址成功";
    }


    //更新收获地址
    public String updateAddress(Address address){

        int i = addressMapper.updateAddress(address);

        return "更新收获地址成功";
    }

    //删除收货地址
    public String delAddressByAddressId(String consumerId, int addressId){

        int i = addressMapper.delAddressByAddressId(addressId);

        return "删除收货地址成功";
    }


    //对用户的订单的提交和扣费
    @Override
    public String addConsumerOrder(String consumerId,Double goodsSellPrice,int goodsId, int goodsNum, int storeId) {

        double y = consumerMapper.selectConsumerBalance(consumerId);
        if(y<(goodsSellPrice*goodsNum)){
            return "余额不足";
        }else{
            Order order = new Order();
            order.setConsumerId(consumerId);
            order.setCreateTime(LocalDateTime.now());
            order.setStoreId(storeId);
            order.setGoodsId(goodsId);
            order.setGoodsNum(goodsNum);
            order.setStatusText("待发货");
            order.setIsDelete(0);
            order.setOrderPrice(goodsSellPrice*goodsNum);
            order.setPayStatus(1);
            order.setPayTime(LocalDateTime.now());
            int i = ordersMapper.insertConsumerOrder(order);
            double newBalance = (y-(goodsSellPrice*goodsNum));
            int x = consumerMapper.updateBalance(consumerId,newBalance);
            return "订单已提交";
        }
    }

    @Override
    public int selectStoreIdByGoodsId(int goodsId) {
        int storeId = goodsMapper.selectStoreIdByGoodsId(goodsId);
        return storeId;
    }



    public String updateBalance(String consumerId,Double balance){
        if(balance<=0){
            return "金额不合适";
        }else{
            int i = consumerMapper.updateBalance(consumerId,balance);
            return "充值成功";
        }
    }



    //充值
    @Override
    public String addBalance(String consumerId, Double addBalance) {


        if(addBalance <= 0){
            return "金额不合适";
        }else{
            double i = consumerMapper.selectConsumerBalance(consumerId);
            double y = i + addBalance;
            int f = consumerMapper.updateBalance(consumerId,y);
            return "充值成功";
        }

    }

    @Override
    public String addBusinessBalance(Double goodsSellPrice, int goodsNum, int storeId) {
        double r = goodsSellPrice*goodsNum;
        double y = consumerMapper.selectBusinessBalance(storeId);
        double f = y+r;
        int i = consumerMapper.addBusinessBalance(f,storeId);
        return "商家已接收";
    }

    public Double selectBusinessBalance(int storeId){
        double r = consumerMapper.selectBusinessBalance(storeId);
        return r;
    }
}
