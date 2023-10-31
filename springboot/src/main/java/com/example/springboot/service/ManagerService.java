package com.example.springboot.service;


import com.example.springboot.pojo.PageBean;
import com.example.springboot.pojo.manager.LoginResponse;

public interface ManagerService {

/* 管理员对自己的操作*/

    //注册
    public String register(String managerName, String password,String role);

    //登录
    public LoginResponse login(String managerName, String password);




/* 管理员对商品分类的操作*/







/*  管理员对商家的操作*/

    //对于商家想要获得发布商品授权（发送信息给管理员请求认证）



    //屏蔽某个商家的权限



    //根据business_id 查询某个商家的信息



    //分页查询商家的信息
    public PageBean selectBusinessByPagination(int page, int pageSize);




/* 对于消费者的操作*/

    //分页查询消费者的信息
    public PageBean selectConsumerByPagination(int page, int pageSize, String mobile, String gender);
}
