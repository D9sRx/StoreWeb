package com.example.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：
 * 作者：单汝轩
 * 日期：2023/10/13 22:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
}