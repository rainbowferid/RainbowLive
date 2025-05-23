package org.web.controller;

import com.wf.captcha.ArithmeticCaptcha;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;
import org.common.component.RedisComponent;
import org.common.constant.Constants;
import org.common.exception.RegisterFailedException;
import org.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web.service.UserInfoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController {
    @Autowired
    RedisComponent redisComponent;
    @Autowired
    UserInfoService userInfoService;
    @RequestMapping("/checkCode")
    public Result<Map<String, String>> checkCode()
    {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        String uuid = redisComponent.setCheckCode(captcha.text());
        String base64 = captcha.toBase64();
        Map<String,String> map = new HashMap<>();
        map.put("checkCodeKey",uuid);
        map.put("checkCode",base64);

        return Result.success(map);
    }

    @PostMapping("/register")
    public Result register(@NotEmpty @Email @Size(max = 80) String email,
                                   @NotEmpty @Size(max = 20) String nickName,
                                   @NotEmpty String checkCodeKey,
                                   @NotEmpty String checkCode,
                                   @NotEmpty @Pattern(regexp = Constants.REGISTER_PASSWORD_REGEX)  String registerPassword)
    {

        String checkCodeValue = redisComponent.getCheckCode(checkCodeKey);

        try{
            if(!checkCodeValue.equalsIgnoreCase(checkCode)) {
                throw new RegisterFailedException("验证码错误");
            }

            userInfoService.register(email,nickName,registerPassword);
            return Result.success();
        }
        finally {
            redisComponent.cleanCheckCode(checkCodeKey);
        }



    }
}
