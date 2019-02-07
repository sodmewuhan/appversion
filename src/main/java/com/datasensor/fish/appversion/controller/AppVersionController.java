package com.datasensor.fish.appversion.controller;

import com.datasensor.fish.appversion.dto.Result;
import com.datasensor.fish.appversion.model.AppVersionInfo;
import com.datasensor.fish.appversion.service.api.AppVersionManage;
import com.datasensor.fish.appversion.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class AppVersionController {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(AppVersionController.class);

    @Autowired
    private AppVersionManage appVersionManage;
    /**
     * 得到最新的版本信息
     * @return
     */
    @PostMapping("/getLastVersion")
    public Result getLastVersion() {
        AppVersionInfo appVersionInfo =  appVersionManage.getLastVersion();
        ResultGenerator resultGenerator = new ResultGenerator();
        Result result = null;
        if (appVersionInfo != null) {
            result = resultGenerator.genSuccessResult(appVersionInfo);
        } else {
            result = resultGenerator.genFailResult("getLastVersion is failure ");
        }

        return result;
    }
}
