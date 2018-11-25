package com.datasensor.fish.appversion.service.impl;

import com.datasensor.fish.appversion.configure.ServiceConfigure;
import com.datasensor.fish.appversion.exception.ServiceException;
import com.datasensor.fish.appversion.model.mapper.AppVersionInfoMapper;
import com.datasensor.fish.appversion.service.api.ServiceUploadApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ServiceUploadAppImpl implements ServiceUploadApp {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(ServiceUploadAppImpl.class);

    @Autowired
    private AppVersionInfoMapper appVersionInfoMapper;

    @Autowired
    private ServiceConfigure serviceConfigure;

    public boolean uploadAppFile(MultipartFile file, String desc) throws ServiceException {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new ServiceException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new ServiceException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                String storeFilePath = serviceConfigure.getStoreFilePath();
                Path rootLocation = Paths.get(storeFilePath);
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception e) {
            throw new ServiceException("Failed to store file " + filename, e);
        }
        return true;
    }

    // 得到需要保存的路径
    private String BuildStorePath() {
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }
}
