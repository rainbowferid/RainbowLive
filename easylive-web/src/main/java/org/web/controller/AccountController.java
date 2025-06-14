package org.web.controller;

import com.wf.captcha.ArithmeticCaptcha;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.common.component.RedisComponent;
import org.common.constant.Constants;
import org.common.entity.dto.TokenUserInfoDTO;
import org.common.exception.RegisterFailedException;
import org.common.result.Result;
import org.common.service.UserInfoService;
import org.common.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping("/login")
    public Result login(@NotEmpty @Email @Size(max = 80) String email,
                        @NotEmpty String checkCodeKey,
                        @NotEmpty String checkCode,
//                        @NotEmpty @Pattern(regexp = Constants.REGISTER_PASSWORD_REGEX)  String password,
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
            String ip = BaseUtils.getIpAddr();//或者在参数中添加request，通过request.getRemoteAddr()获取
            TokenUserInfoDTO tokenUserInfoDTO = userInfoService.login(email, password, ip);
            BaseUtils.saveTokenToCookie(tokenUserInfoDTO.getToken(),response);

            return Result.success(tokenUserInfoDTO);
        }
        finally {
            redisComponent.cleanCheckCode(checkCodeKey);
            Cookie[] cookies = request.getCookies();
            if(cookies != null)
            {
                for (Cookie cookie : cookies) {
                    if(cookie.getName().equals(Constants.TOKEN_WEB))
                    {
                        redisComponent.cleanToken(cookie.getValue());
                        break;
                    }
                }
            }

        }

    }
    @RequestMapping("/autoLogin")
    public Result autoLogin(HttpServletRequest request,HttpServletResponse response) {
        TokenUserInfoDTO tokenUserInfoDTO = redisComponent.getTokenUserInfo(request.getHeader(Constants.TOKEN_WEB));
        if (tokenUserInfoDTO == null)
        {
            return Result.error("请先登录");
        }
        String token = tokenUserInfoDTO.getToken();
        if (tokenUserInfoDTO.getExpiredAt() - System.currentTimeMillis()<=Constants.REDIS_EXPIRE_TIME_DAY)
        {
            redisComponent.setTokenUserInfo(tokenUserInfoDTO);//刷新token有效期，重新生成token
            BaseUtils.saveTokenToCookie(tokenUserInfoDTO.getToken(),response);//将生成的token传入cookie
        }
        BaseUtils.saveTokenToCookie(tokenUserInfoDTO.getToken(),response);//token不变,重置cookie时间
        return Result.success(tokenUserInfoDTO);
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
