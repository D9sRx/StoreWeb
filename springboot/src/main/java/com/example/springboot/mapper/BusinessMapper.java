package com.example.springboot.mapper;

import com.example.springboot.pojo.business.Business;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BusinessMapper {

    //添加·商家
    public int insertBusiness(Business business);

    //修改商家信息
    public int updateBusiness(Business business);

    //删除商家信息
    public int delBusinessByStoreId(int storeId);

    //查询商家数据的总记录数
    @Select("select count(*) from business")
    public int selectCount();

    //条件分页查询数据
    public List<Business> selectBusinessByPagination(int start, int pageSize);

    //查询商家信息（根据storeId）
    public Business selectBusinessByStoreId(int storeId);

    //查询商家信息（根据mobile）
    public Business selectBusinessByMobile(String mobile);

    public Business selectBusinessByNickName(String nickName);

    int deleteOrder(int orderId);

    int sendOrder(int orderId);
}
