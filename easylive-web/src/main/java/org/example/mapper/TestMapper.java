package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.UserInfo;

@Mapper
public interface TestMapper {

    @Select("select * from easylive.user_info ")
    UserInfo getAllUserInfo();
}
