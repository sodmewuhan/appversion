package com.datasensor.fish.appversion.service.impl;

import com.datasensor.fish.appversion.configure.ServiceConfigure;
import com.datasensor.fish.appversion.model.UserInfo;
import com.datasensor.fish.appversion.model.mapper.UserInfoMapper;
import com.datasensor.fish.appversion.service.api.ImgHeadfaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 用户头像服务类
 */
@Service
public class ImgHeadfaceServiceImpl implements ImgHeadfaceService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ImgHeadfaceServiceImpl.class);

    @Autowired
    ServiceConfigure serviceConfigure;   // 服务配置

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public void headfaceUpload(MultipartFile file, String username) throws Exception {

        Assert.notNull(file,"上传文件不能为空");
        Assert.notNull(username,"用户名不能为空");
        // 1 、判断用户是否存在
        UserInfo userInfo = userInfoMapper.selectByUsername(username);
        if (userInfo == null) {
            throw new Exception("用户名" + username + "不存在");
        }
        // 2、生成保存路径
        String savePath = serviceConfigure.getImagesPath() + File.separator + username;
        File dest = new File(savePath);

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
        try {
            //上传文件
            file.transferTo(dest); //保存文件
            userInfo.setHeadfaceUrl(savePath);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
            LOGGER.info("save the username " + username + " headface to dest" + dest.getAbsolutePath());
        } catch (IOException e) {
            new Exception("the file save is error ,the error message is " + e.getMessage());
        }
    }
}
