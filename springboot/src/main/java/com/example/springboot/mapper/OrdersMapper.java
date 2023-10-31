package com.example.springboot.mapper;

import com.example.springboot.pojo.consumer.myOrder.Order;
import com.example.springboot.pojo.consumer.myOrder.mvc.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper {
/* 订单 */

    //添加订单
    public int insertOrder(Order order);

    public int insertConsumerOrder(Order order);

    //修改某订单的信息
    public int updateOrder(Order order);

    //删除订单
    public int delOrder(int orderId);

    //查询某个订单的数据

    public OrderInfo selectOrderInfoBYOrderId(int orderId);

    public Order selectOrderByOrderId(int orderId);

    //查看某个消费者的所有订单数据（未删除）
    public List<Order> selectOrderByConsumerId(String consumerId);

    //查询某个商家的所有订单信息
    public List<Order> selectOrderByStoreId(int storeId);
    public List<Integer> selectOrderIdsByPagination(int storeId, int start, int pageSize, String statusText, LocalDateTime createTimeStart, LocalDateTime createTimeEnd);


}






















