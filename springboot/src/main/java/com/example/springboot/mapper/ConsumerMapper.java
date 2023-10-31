package com.example.springboot.mapper;

import com.example.springboot.pojo.consumer.Consumer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConsumerMapper {
/* 对于自己的操作 */
    //添加用户
    public int insertConsumer(Consumer consumer);

    //更新用户信息
    public int updateConsumer(Consumer consumer);

    //删除用户信息
    public int delConsumer(String consumerId);

    //查询用户数据的总记录数
    @Select("select count(*) from consumer")
    public int selectCount();

    public Double selectConsumerBalance(String nickName);

    //条件分页查询消费者的数据
    public List<Consumer> selectConsumerByPagination(int start, int pageSize, String mobile, String gender);

    //查询用户数据（根据consumerId）
    public Consumer selectConsumerByConsumerId(String consumerId);

    //查询用户信息（根据电话号码）
    public Consumer selectConsumerByMobile(String mobile);

    public Consumer selectConsumerByNickName(String nickName);

    public int updateBalance(String consumerId,Double newBalance);

    public int addBusinessBalance(double r, int storeId);

    public int selectBusinessBalance(int storeId);
}
