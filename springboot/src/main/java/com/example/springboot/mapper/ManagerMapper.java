package com.example.springboot.mapper;

import com.example.springboot.pojo.manager.Manager;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerMapper {

    //添加管理员数据
    public int insertManager(Manager manager);

    //修改管理员数据
    public int updateManager(Manager manager);

    //删除管理员数据
    public int delManager(String managerName);

    //查询管理员数据（根据管理员的名字）
    public Manager selectManager(String managerName);
}
