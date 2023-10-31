package com.example.springboot.pojo.GoodsCategory.mvc;


import com.example.springboot.pojo.GoodsCategory.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class LGoodsCategory implements Category {
    private int categoryId ;
    private String name ;
    private int parentId ;
    private int status ;
    private String imageUrl;
    private int sort ;
    private List<KGoodsCategory> children ;
}
