package org.common.component;

import jakarta.annotation.Resource;
import org.common.constant.Constants;
import org.common.entity.dto.TokenUserInfoDTO;
import org.common.entity.po.UserInfo;
import org.common.utils.RedisUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RedisComponent {

    @Resource
    RedisUtils redisUtils;


    //  验证码的key为uuid，value为验证码
    //  返回验证码的uuid
    public String setCheckCode(String code) {
        String uuid = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE + uuid, code, Constants.REDIS_EXPIRE_TIME_MINUTE*10);
        return uuid;
    }

    public String getCheckCode(String checkCodeKey) {

        if (redisUtils.keyExists(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey)) {

            return redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey).toString();
        }
        else {
            return "failed";
        }
    }

    public void cleanCheckCode(String checkCodeKey) {
        redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
    }

    public void setTokenUserInfo(TokenUserInfoDTO tokenUserInfoDTO) {

        String token = UUID.randomUUID().toString();
        tokenUserInfoDTO.setToken(token);//这里tokenUserInfoDTO更改了token，外部的变量也更改
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_WEB + token, tokenUserInfoDTO, Constants.REDIS_EXPIRE_TIME_DAY*7);
        //key:value 形式
    }

    public void cleanToken(String token) {
        redisUtils.delete(Constants.REDIS_KEY_TOKEN_WEB + token);
    }

    public TokenUserInfoDTO getTokenUserInfo(String token) {
        Object tokenUserInfoDTO = redisUtils.get(Constants.REDIS_KEY_TOKEN_WEB + token);
        return (TokenUserInfoDTO) tokenUserInfoDTO;
    }

    public String saveToken4Admin(String account) {

        String token = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_ADMIN + token, account, Constants.REDIS_EXPIRE_TIME_DAY);
        return token;
    }

    public void cleanAdminToken(String token) {
        redisUtils.delete(Constants.REDIS_KEY_TOKEN_ADMIN + token);
    }

    public String getAdminTokenInfo(String token) {

        return  (String) redisUtils.get(Constants.REDIS_KEY_TOKEN_ADMIN + token);
    }
}
