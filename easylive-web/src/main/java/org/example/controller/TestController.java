package org.example.controller;

import org.example.entity.UserInfo;
import org.example.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TestMapper testMapper;

    @RequestMapping("/test")
    public UserInfo test() {

        UserInfo info = testMapper.getAllUserInfo();

        return info;
    }
}
