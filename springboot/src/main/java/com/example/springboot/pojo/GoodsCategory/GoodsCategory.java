package com.example.springboot.pojo.GoodsCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class GoodsCategory implements Category{
    private int categoryId ;
    private String name ;
    private int parentId ;
    private int status ;
    private String imageUrl;
    private int sort ;
}
