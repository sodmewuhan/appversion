package com.datasensor.fish.appversion.model.mapper;

import com.datasensor.fish.appversion.model.AppVersionInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppVersionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppVersionInfo record);

    int insertSelective(AppVersionInfo record);

    AppVersionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppVersionInfo record);

    int updateByPrimaryKey(AppVersionInfo record);
}