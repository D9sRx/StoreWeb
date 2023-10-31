package com.example.springboot.mapper;

import com.example.springboot.pojo.GoodsCategory.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsCategoryMapper {

    //添加商品分类
    public int insertGoodsCategory(GoodsCategory goodsCategory);

    //删除商品分类（根据分类id）
    public int delGoodsCategoryByCategoryId(int categoryId);

    //删除商品分类（根据父分类id）
    public int delGoodsCategoryByParentId(int parentId);

    //修改商品分类信息
    public int updateGoodsCategory(GoodsCategory goodsCategory);



    //根据父类id查询所有子类的类别数据
    List<GoodsCategory> selectCategoryByParentId(int parentId);


    //根据分类id查询对应类别数据
    GoodsCategory selectCategoryByCategoryId(int categoryId);


}




















