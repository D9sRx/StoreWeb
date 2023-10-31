package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 功能：
 * 作者：单汝轩
 * 日期：2023/10/13 22:19
 */
@Mapper
public interface UserMapper {
    @Insert("insert into `user`(username,password,name,phone,email,address,avatar) " +
            "values(#{username},#{password},#{name},#{phone},#{email},#{address},#{avatar}) ")
    void insert(User user);


    @Select("select * from `user_for_test`")
    void selectAll(User user);
}