package org.admin.controller;

import com.wf.captcha.ArithmeticCaptcha;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.common.component.RedisComponent;
import org.common.config.AppConfig;
import org.common.constant.Constants;
import org.common.entity.dto.TokenUserInfoDTO;
import org.common.exception.BaseException;
import org.common.exception.RegisterFailedException;
import org.common.result.Result;
import org.common.utils.BaseUtils;
import org.common.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController extends ABaseController{
    @Autowired
    RedisComponent redisComponent;

    @Autowired
    AppConfig appConfig;
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

    @PostMapping("/login")
    public Result login(@NotEmpty @Size(max = 80) String account,
                        @NotEmpty String checkCodeKey,
                        @NotEmpty String checkCode,
                        @NotEmpty String password,
                        HttpServletResponse response,
                        HttpServletRequest request
                        )
    {

        String checkCodeValue = redisComponent.getCheckCode(checkCodeKey);
        try{
            if(!checkCodeValue.equalsIgnoreCase(checkCode)) {
                throw new RegisterFailedException("验证码错误");
            }
            if(!account.equals(appConfig.getAdminAccount())||!password.equals(StringTools.encodeByMd5(appConfig.getAdminPassword())))
            {
                throw new BaseException("用户名或密码错误");
            }
            String token = redisComponent.saveToken4Admin(account);
            saveTokenToCookie(token,response);
            return Result.success(account);
        }
        finally {
            redisComponent.cleanCheckCode(checkCodeKey);
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(Constants.TOKEN_ADMIN))
                {
                    redisComponent.cleanAdminToken(cookie.getValue());
                    break;
                }
            }
        }
    }

    @RequestMapping("/logout")
    public Result logout(HttpServletRequest request,HttpServletResponse response)
    {
        BaseUtils.cleanToken(response);

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(Constants.TOKEN_WEB))
            {
                redisComponent.cleanToken(cookie.getValue());
            }
        }
        return Result.success();
    }



}
