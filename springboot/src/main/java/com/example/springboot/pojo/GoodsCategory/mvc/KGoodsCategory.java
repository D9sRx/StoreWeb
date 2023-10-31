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
public class KGoodsCategory implements Category {
    private String name ;
    private String imageUrl;
    private int sort ;
    private List<KGoodsCategory> children ;
}
















