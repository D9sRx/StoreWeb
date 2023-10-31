package com.example.springboot.utils;

import com.example.springboot.mapper.GoodsCategoryMapper;
import com.example.springboot.pojo.GoodsCategory.Category;
import com.example.springboot.pojo.GoodsCategory.GoodsCategory;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;


@Component
public class GoodsCategoryUtils {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;


    //无敌之我是递归
    public void setGoodsCategory(int parentId,Category category) throws Exception{
        //用于封装子类处理后的数据
        HashMap<Integer,Category> categories = new HashMap<>();


        //获取该类别的子类别数据
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectCategoryByParentId(parentId);
        if(goodsCategories.size()==0) {
            return;
        }

    /* 此处用反射实现动态返回数据较为合适 */

        for (GoodsCategory goodsCategory1 : goodsCategories) {
            int categoryId = goodsCategory1.getCategoryId();
            Category category1 = category.getClass().newInstance();

            List<Field> list = Arrays.stream(goodsCategory1.getClass().getDeclaredFields()).toList();

            List<Field> list1 = Arrays.stream(category1.getClass().getDeclaredFields()).toList();

            for (Field field : list) {
                if(list1.contains(field)){
                    String name = field.getName();
                    field.setAccessible(true);
                    Object o = field.get(goodsCategory1);

                    for (Field field1 : list1) {
                        field1.setAccessible(true);
                        String name1 = field1.getName();
                        if(name1.equals(name)){
                            field1.set(category1,o);
                        }
                    }
                }
            }
            categories.put(categoryId,category1);
        }

        //封装数据
        Field children = category.getClass().getDeclaredField("children");
        children.setAccessible(true);
        children.set(category,categories.values().stream().toList());


        //递归封装子类数据

        for (Map.Entry<Integer, Category> integerCategoryEntry : categories.entrySet()) {
            setGoodsCategory(integerCategoryEntry.getKey(), integerCategoryEntry.getValue());
        }

    }
}











