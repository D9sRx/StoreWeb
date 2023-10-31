package com.example.springboot.mapper;

import com.example.springboot.pojo.common.Yzm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonMapper {
/* 对验证码表进行操作 */
    //添加验证码
    public int insertYzm(Yzm yzm);

    //查询验证码
    public Yzm selectYzm(String picKey);

    //删除该验证码信息
    public int delYzm(String picKey);

}
