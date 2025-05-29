package org.common.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TokenUserInfoDTO implements Serializable {

    private String userId;
    private String nickName;

    private String avatar;//头像
    private String token;
    private Long expiredAt;

    private Integer fansCount;
    private Integer currentCoinCount;
    private Integer followCount;


}
