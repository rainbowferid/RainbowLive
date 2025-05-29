package org.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.common.entity.po.UserInfo;

@Mapper
public interface UserInfoMapper {

    @Select("select * from easylive.user_info where email=#{email}")
    public UserInfo getUserInfoByEmail(String email);

    @Select("select * from user_info where nick_name = #{nickName}")
    public UserInfo getUserInfoByNickName(String nickName);


    void addUserInfo(UserInfo userInfo);

    void updateUserInfo(UserInfo userInfo);
}
