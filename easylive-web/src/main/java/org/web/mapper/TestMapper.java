package org.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.common.entity.po.UserInfo;

@Mapper
public interface TestMapper {

    @Select("select * from easylive.user_info ")
    UserInfo getAllUserInfo();
}
