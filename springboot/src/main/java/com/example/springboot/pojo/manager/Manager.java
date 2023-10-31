package com.example.springboot.pojo.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Manager {
    private String managerName ;
    private String password ;
    private String role ;

}
