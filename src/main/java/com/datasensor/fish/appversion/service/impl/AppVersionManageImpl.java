package com.datasensor.fish.appversion.service.impl;

import com.datasensor.fish.appversion.model.AppVersionInfo;
import com.datasensor.fish.appversion.model.mapper.AppVersionInfoMapper;
import com.datasensor.fish.appversion.service.api.AppVersionManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppVersionManageImpl  implements AppVersionManage {

    @Autowired
    private AppVersionInfoMapper appVersionInfoMapper;

    @Override
    public AppVersionInfo getLastVersion() {
        return appVersionInfoMapper.getLastVersion();
    }

    @Override
    public AppVersionInfo getAppinfoByVersion(String version) {

        return appVersionInfoMapper.getAppinfoByVersion(version);
    }
}
