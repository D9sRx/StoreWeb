package com.example.springboot.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.example.springboot.mapper.CommonMapper;
import com.example.springboot.pojo.common.Yzm;
import com.example.springboot.utils.AliOSSUtils;
import com.example.springboot.utils.Result;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;


@RestController
@Slf4j
@RequestMapping("/common")
@CrossOrigin        /*解决跨域问题*/
public class CommonController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    private LineCaptcha lineCaptcha;

    @Autowired
    private CommonMapper commonMapper;


    @PostMapping("/upload")
    //文件上传
    public Result upload(MultipartFile file) throws IOException {
        log.info("file: {}",file);
        String imageUrl = aliOSSUtils.upload(file);
        return Result.success(imageUrl);
    }

    @GetMapping("/getCodeImg")
    //获取图形验证码的图片
    public void getCodeImg(HttpServletResponse response) throws IOException {
        // 随机生成 4 位验证码
        RandomGenerator randomGenerator = new RandomGenerator( 4);
        // 定义图片的显示大小
        lineCaptcha = CaptchaUtil.createLineCaptcha(100, 30);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        try( OutputStream outputStream = response.getOutputStream()) {
            // 调用父类的 setGenerator() 方法，设置验证码的类型
            lineCaptcha.setGenerator(randomGenerator);
            // 输出到页面
            lineCaptcha.write(outputStream);
            // 打印日志
            log.info("生成的验证码:{}", lineCaptcha.getCode());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将生成的图片，加入验证码表
        commonMapper.insertYzm(new Yzm(null,lineCaptcha.getCode(),lineCaptcha.getImageBytes().toString()));
    }

    @GetMapping("/getMsgCode")
    //获取短信验证码
    public Result getMsgCode(String picCode, String picKey){
        //查询验证码
        Yzm yzm = commonMapper.selectYzm(picKey);
        //删除该验证码信息
        int i = commonMapper.delYzm(picKey);
        String path = "D:/HomeWork/yzm/"+picKey+"-yzm.jpg";
        File file = new File(path);
        file.delete();
        if(yzm == null){
            return Result.error("该图形验证码已失效，请刷新后重试");
        }
        if(picCode.equals(yzm.getPicCode())){
            return Result.success("197088");
        }
        return Result.error("输入的图形验证码错误");
    }

    @GetMapping("/getPicCode")
    //获取图形验证码
    public Result getPicCode(){
        // 随机生成 5 位验证码
        RandomGenerator randomGenerator = new RandomGenerator(5);
        //定义图片的大小
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        // 调用父类的 setGenerator() 方法，设置验证码的类型
        lineCaptcha.setGenerator(randomGenerator);

        //将该验证码的图片写入到文件中
        UUID uuid = UUID.randomUUID();
        String path = "D:/HomeWork/yzm/"+uuid+"-yzm.jpg";
        lineCaptcha.write(path);
        //将生成的图片，加入验证码表
        Yzm yzm = new Yzm(null, lineCaptcha.getCode(), uuid.toString());
        commonMapper.insertYzm(yzm);
        yzm.setPicUrl(path);
        yzm.setPicCode(null);
        return Result.success(yzm);
    }
}