package com.example.springboot.pojo.Goods.mvc;

import com.example.springboot.pojo.Goods.GoodsDetailImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class GoodsDetailImageInfo {

    private List<GoodsDetailImage> swiperImage ;  //轮播照片
    private List<GoodsDetailImage> staticImage ;  //静态详情照片
}
