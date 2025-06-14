package org.common.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.common.entity.po.UserInfo;

import java.util.List;

@Mapper
public interface TestMapper {

    @Select("select * from easylive.user_info ")
    List<UserInfo> getAllUserInfo();
}
