package com.example.springboot.pojo.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Consumer {
       private  String  consumerId  ;
       private  String  password;
       private  String  mobile;
       private  String  nickName ;
       private  String  gender ;
       private  int  gradeId ;
       private  int  addressId ;
       private  String  country ;
       private  String  province ;
       private  String  city ;
       private  double  balance ;
       private  int  points ;
       private  double  consumeMoney ;
       private  LocalDateTime  createTime ;
       private LocalDateTime lastLoginTime;
       private int isDelete ;
}
