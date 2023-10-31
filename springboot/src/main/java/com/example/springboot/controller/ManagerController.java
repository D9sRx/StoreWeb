package com.example.springboot.controller;

import com.example.springboot.pojo.PageBean;
import com.example.springboot.pojo.manager.LoginResponse;
import com.example.springboot.service.ManagerService;
import com.example.springboot.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/manager")
@CrossOrigin        /*解决跨域问题*/
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/register")
    //注册
    public Result register(String managerName, String password,String role){
        String msg = managerService.register(managerName,password,role);
        if("注册成功".equals(msg)){
            return Result.success(msg);
        }
        return Result.error(msg);
    }

    @GetMapping("/login")
    //登录
    public Result login(String managerName, String password){
        try {
            LoginResponse response = managerService.login(managerName, password);
            return Result.success(response);
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/selectConsumerByPagination")
    //条件分页查询消费者的数据
    public Result selectConsumerByPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             String mobile, String gender){
        PageBean pageBean = managerService.selectConsumerByPagination(page, pageSize, mobile, gender);
        return Result.success(pageBean);
    }

    @GetMapping("/selectBusinessByPagination")
    //分页查询商家的信息
    public Result selectBusinessByPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int pageSize){
        PageBean pageBean = managerService.selectBusinessByPagination(page, pageSize);
        return Result.success(pageBean);
    }
}