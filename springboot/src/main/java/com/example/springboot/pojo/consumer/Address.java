package com.example.springboot.pojo.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Address {
    private int addressId ;
    private String name;
    private String phone;
    private String province ;
    private String city;
    private String region;
    private String detail;
    private String consumerId;
}
