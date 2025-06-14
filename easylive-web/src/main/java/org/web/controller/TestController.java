package org.web.controller;

import com.alibaba.fastjson2.JSON;
import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.common.entity.po.UserInfo;
import org.common.mappers.TestMapper;
import org.common.result.Result;
import org.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@Slf4j
public class TestController {

    @Autowired
    TestMapper testMapper;
    @Resource
    RedisUtils  redisUtils;

    @RequestMapping("/mysql")
    public List<UserInfo> testmysql(UserInfo userInfo) {
        System.out.println(userInfo);

        return testMapper.getAllUserInfo();
    }

    @RequestMapping("/redis")
    public String testredis() {
        redisUtils.set("test", "123");

        return "abc";
    }
}
