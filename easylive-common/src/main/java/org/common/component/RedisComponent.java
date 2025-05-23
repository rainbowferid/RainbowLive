package org.common.component;

import jakarta.annotation.Resource;
import org.common.constant.Constants;
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
        else
        {

            return "failed";
        }

    }

    public void cleanCheckCode(String checkCodeKey) {

        redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);

    }
}
