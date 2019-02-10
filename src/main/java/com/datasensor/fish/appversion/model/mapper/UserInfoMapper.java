package com.datasensor.fish.appversion.model.mapper;

import com.datasensor.fish.appversion.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    /**
     * 根据用户名得到用户信息
     * @param username
     * @return
     */
    UserInfo selectByUsername(@Param("username") String username);
}