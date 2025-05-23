package org.web.service.impl;

import org.common.constant.Constants;
import org.common.entity.UserInfo;
import org.common.enums.UserSexEnum;
import org.common.enums.UserStatusEnum;
import org.common.exception.RegisterFailedException;
import org.common.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web.mapper.UserInfoMapper;
import org.web.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public Boolean register(String email, String nickName, String registerPassword) {

        if(userInfoMapper.getUserInfoByEmail(email)!=null)
        {
            throw new RegisterFailedException("邮箱已被注册");
        }
        if(userInfoMapper.getUserInfoByNickName(nickName)!=null)
        {
            throw new RegisterFailedException("昵称已被注册");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(StringTools.getRandomNumber(Constants.ID_LENGTH));
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);
        userInfo.setPassword(registerPassword);

        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setSex(UserSexEnum.SECRECT.getSex());
        userInfo.setJoinTime(new java.sql.Timestamp(System.currentTimeMillis()));

        userInfo.setTheme(Constants.THEME_DEFAULT);
        userInfo.setTotalCoinCount(Constants.COIN_DEFAULT);
        userInfo.setCurrentCoinCount(Constants.COIN_DEFAULT);

        userInfoMapper.addUserInfo(userInfo);

        return true;
    }
}
