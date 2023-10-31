package com.example.springboot.pojo.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Business {
    private int storeId ; //商家的门牌号
    private String mobile ; //商家的电话号码
    private String password ; //商家的密码
    private String nickName ; //昵称
    private int status ; //该商家的可运行状态
    private double balance ; //余额
    private double profitMoney ; //盈利的金额
    private int isDelete ;  //是否被禁用
}









