package com.datasensor.fish.appversion.service.api;

import com.datasensor.fish.appversion.model.AppVersionInfo;

/**
 * 版本管理
 */
public interface AppVersionManage {

    /**
     *  得到最新的版本信息
     * @return
     */
    public AppVersionInfo getLastVersion();

    /**
     * 根据版本号获取APP信息
     * @return
     */
    public AppVersionInfo getAppinfoByVersion(String version);
}
