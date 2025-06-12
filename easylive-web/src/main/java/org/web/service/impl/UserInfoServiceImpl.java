package org.web.service.impl;

import org.common.component.RedisComponent;
import org.common.constant.Constants;
import org.common.entity.dto.TokenUserInfoDTO;
import org.common.entity.po.UserInfo;
import org.common.enums.UserSexEnum;
import org.common.enums.UserStatusEnum;
import org.common.exception.BaseException;
import org.common.exception.RegisterFailedException;
import org.common.utils.CopyTools;
import org.common.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.web.mapper.UserInfoMapper;
import org.web.service.UserInfoService;

import java.beans.Encoder;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    private RedisComponent redisComponent;

    @Override
    public void register(String email, String nickName, String registerPassword) {

        if(userInfoMapper.getUserInfoByEmail(email)!=null)
        {
            throw new RegisterFailedException("邮箱已被注册");
        }
        if(userInfoMapper.getUserInfoByNickName(nickName)!=null)
        {
            throw new RegisterFailedException("昵称已被注册");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);

        userInfo.setPassword(DigestUtils.md5DigestAsHex(registerPassword.getBytes()));
//        userInfo.setPassword(registerPassword);

        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setSex(UserSexEnum.SECRECT.getSex());
        userInfo.setJoinTime(new java.sql.Timestamp(System.currentTimeMillis()));

        userInfo.setTheme(Constants.THEME_DEFAULT);
        userInfo.setTotalCoinCount(Constants.COIN_DEFAULT);
        userInfo.setCurrentCoinCount(Constants.COIN_DEFAULT);

        int retries = 0;
        while (retries < Constants.MAX_RETRIES)
        {
            try {
                userInfo.setUserId(StringTools.getRandomNumber(Constants.ID_LENGTH));
                userInfoMapper.addUserInfo(userInfo);
                break;
            }
            catch (DuplicateKeyException e){
                retries++;
                if(retries >= Constants.MAX_RETRIES)
                {
                    throw new RegisterFailedException("注册失败,请稍后再试");
                }
            }
        }

    }

    @Override
    public TokenUserInfoDTO login(String email, String password, String ip) {

        UserInfo userInfo = userInfoMapper.getUserInfoByEmail(email);
        if(userInfo==null||!userInfo.getPassword().equals(password))
        {
            throw new BaseException("用户或密码错误");
        }
        if(userInfo.getStatus()!=UserStatusEnum.ENABLE.getStatus())
        {
            throw new BaseException("用户状态异常");
        }

        userInfo.setLastLoginTime(new java.sql.Timestamp(System.currentTimeMillis()));
        userInfo.setLastLoginIp(ip);
        userInfoMapper.updateUserInfo(userInfo);

        TokenUserInfoDTO tokenUserInfoDTO = CopyTools.copy(userInfo, TokenUserInfoDTO.class);

        tokenUserInfoDTO.setExpiredAt(System.currentTimeMillis()+Constants.REDIS_EXPIRE_TIME_DAY*7);
        redisComponent.setTokenUserInfo(tokenUserInfoDTO);

        return tokenUserInfoDTO;

    }


}
