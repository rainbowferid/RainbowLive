package org.web.service;

import org.common.entity.dto.TokenUserInfoDTO;

public interface UserInfoService {


    void register(String email, String nickName, String registerPassword);

    TokenUserInfoDTO login(String email, String password, String ip);
}
