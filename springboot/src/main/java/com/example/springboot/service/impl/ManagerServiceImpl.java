package com.example.springboot.service.impl;

import com.example.springboot.mapper.BusinessMapper;
import com.example.springboot.mapper.ConsumerMapper;
import com.example.springboot.mapper.GoodsCategoryMapper;
import com.example.springboot.mapper.ManagerMapper;
import com.example.springboot.pojo.GoodsCategory.GoodsCategory;
import com.example.springboot.pojo.GoodsCategory.mvc.KGoodsCategory;
import com.example.springboot.pojo.PageBean;
import com.example.springboot.pojo.business.Business;
import com.example.springboot.pojo.consumer.Consumer;
import com.example.springboot.pojo.manager.LoginResponse;
import com.example.springboot.pojo.manager.Manager;
import com.example.springboot.service.ManagerService;
import com.example.springboot.utils.GoodsCategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsCategoryUtils goodsCategoryUtils;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private BusinessMapper businessMapper;
/* 管理员对自己的操作*/

    //注册
    public String register(String managerName, String password,String role){
        //先查询该是否是已经注册过了的
        Manager s_manager = managerMapper.selectManager(managerName);
        if(s_manager != null){    //已注册
            return "该账号已经注册";
        }
        //进行注册
        Manager manager = new Manager();
        manager.setManagerName(managerName);
        manager.setPassword(password);
        manager.setRole(role);
        int i = managerMapper.insertManager(manager);
        return "注册成功";
    }

    //登录
    public LoginResponse login(String managerName, String password){
        LoginResponse response = new LoginResponse();
        //先查询该商家是否是已经注册过了的
        Manager s_manager = managerMapper.selectManager(managerName);
        if(s_manager == null){ //未注册
            throw new RuntimeException("该账号未注册，请先注册");
        }
        //进行登录校验
        if(!s_manager.getPassword().equals(password)){   //密码不对
            throw new RuntimeException("您输入的密码不对，请重新输入");
        }
        //进行登录
        response.setRole(s_manager.getRole());
        response.setManagerName(s_manager.getManagerName());
        response.setMessage("登录成功");
        int storeId=0;

        if (s_manager.getRole().equals("business")){
            Business business = businessMapper.selectBusinessByNickName(s_manager.getManagerName());
            storeId=business.getStoreId();
        }
        response.setStoreId(storeId);
        return response;
    }




/* 管理员对商品分类的操作*/

    //查询所有的商品分类信息
    public KGoodsCategory selectAllGoodsCategory() throws Exception {
        //查询最高级别的商品类别信息
        GoodsCategory goodsCategory = goodsCategoryMapper.selectCategoryByCategoryId(0);

        //封装第一层类别信息
        KGoodsCategory kGoodsCategory = new KGoodsCategory(goodsCategory.getName(),
                goodsCategory.getImageUrl(), goodsCategory.getSort(), null);

        //利用递归的思想嵌套封装下面层级的信息
        goodsCategoryUtils.setGoodsCategory(0,kGoodsCategory);

        return kGoodsCategory;
    }


    //添加商品分类信息
    public String addGoodsCategory(int parentId, int categoryId, String name, String imageUrl){
        //查询父类id是否存在
        GoodsCategory parent = goodsCategoryMapper.selectCategoryByCategoryId(parentId);
        if(parent == null){
            return "该parentId不存在，添加商品分类失败";
        }
        //查看该categoryId是否已经使用过了
        GoodsCategory son = goodsCategoryMapper.selectCategoryByCategoryId(categoryId);
        if(son != null){
            return "该分类id已经创建，此次添加商品分类失败";
        }
        //添加商品分类
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setParentId(parentId);
        goodsCategory.setCategoryId(categoryId);
        goodsCategory.setImageUrl(imageUrl);
        goodsCategory.setName(name);
        goodsCategoryMapper.insertGoodsCategory(goodsCategory);

        return "添加商品分类"+name+"成功";
    }

    //修改商品分类
    public String updateGoodsCategory(GoodsCategory goodsCategory){
        int i = goodsCategoryMapper.updateGoodsCategory(goodsCategory);
        return "修改成功";
    }


    //删除商品分类（根据categoryId）
    public String delGoodsCategoryBycategoryId(int categoryId){
        int i = goodsCategoryMapper.delGoodsCategoryByCategoryId(categoryId);
        return "删除成功";
    }

    //删除商品分类（根据parentId）
    public String delGoodsCategoryByParentId(int parentId){
        int i = goodsCategoryMapper.delGoodsCategoryByParentId(parentId);
        return "删除成功";
    }



/*  管理员对商家的操作*/

    //对于商家想要获得发布商品授权（发送信息给管理员请求认证）



    //屏蔽某个商家的权限



    //根据business_id 查询某个商家的信息



    //分页查询商家的信息
    public PageBean selectBusinessByPagination(int page, int pageSize){
        //查询商家数据的总记录数
        int total = businessMapper.selectCount();

        //条件分页查询数据
        List<Business> businessList = businessMapper.selectBusinessByPagination((page - 1) * pageSize, pageSize);

        //封装pageBean
        PageBean pageBean = new PageBean(total, businessList);

        return pageBean;
    }




/* 对于消费者的操作*/

    //分页查询消费者的信息
    public PageBean selectConsumerByPagination(int page, int pageSize, String mobile, String gender){
        //查询消费者数据的总记录数
        int total = consumerMapper.selectCount();

        //条件分页查询数据
        List<Consumer> consumers = consumerMapper.selectConsumerByPagination((page - 1) * pageSize, pageSize, mobile, gender);

        //封装pageBean
        PageBean pageBean = new PageBean(total, consumers);

        return pageBean;
    }
















}














