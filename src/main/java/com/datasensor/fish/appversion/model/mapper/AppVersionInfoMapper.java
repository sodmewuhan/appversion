package com.datasensor.fish.appversion.model.mapper;

import com.datasensor.fish.appversion.model.AppVersionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppVersionInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppVersionInfo record);

    int insertSelective(AppVersionInfo record);

    AppVersionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppVersionInfo record);

    int updateByPrimaryKey(AppVersionInfo record);

    /**
     * 得到最后的更新版本
     * @return
     */
    AppVersionInfo getLastVersion();

    /**
     * 根据版本号得到版本信息
     * @param appversion
     * @return
     */
    AppVersionInfo getAppinfoByVersion(@Param("appversion") String appversion);
}