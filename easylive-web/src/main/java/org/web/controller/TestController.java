package org.web.controller;

import com.alibaba.fastjson2.JSON;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.common.entity.po.UserInfo;
import org.common.result.Result;
import org.common.utils.RedisUtils;
import org.web.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class TestController {

    @Autowired
    TestMapper testMapper;
    @Resource
    RedisUtils  redisUtils;

    @RequestMapping("/json")
    public String testjson() {

        UserInfo info = testMapper.getAllUserInfo();
        String jsonString = JSON.toJSONString(info);
        System.out.println(info);
        System.out.println(jsonString);
        return jsonString;
    }
    @RequestMapping("/mysql")
    public UserInfo testmysql() {

        UserInfo info = testMapper.getAllUserInfo();

        return info;
    }
    @RequestMapping("/test")
    public String test() {

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        String base64 = captcha.toBase64();
        String jsonString = JSON.toJSONString(base64);
        return jsonString;
    }

    @RequestMapping("/captcha")
    public Result<String> testcaptcha() {

        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        System.out.println(Result.success(captcha.toBase64()));
        return Result.success(captcha.toBase64());
    }

    @RequestMapping("/redis")
    public String testredis() {
        redisUtils.set("test", "123");

        return "abc";
    }
}
