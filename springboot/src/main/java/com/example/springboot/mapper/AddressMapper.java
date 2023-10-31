package com.example.springboot.mapper;

import com.example.springboot.pojo.consumer.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressMapper {

    //添加收货地址
    public int insertAddress(Address address);

    //修改收货地址
    public int updateAddress(Address address);

    //删除收货地址（根据address_id）
    public int delAddressByAddressId(int addressId);

    //查询该用户所添加的收获地址的记录数
    @Select("select count(*) from address where consumer_id = #{consumerId}")
    public int addressCount(String consumerId);

    //查询所有的收获地址
    public List<Address> selectAllAddress();

    //根据address_id 查询收货地址
    public Address selectAddressByAddressId(int addressId);

}















