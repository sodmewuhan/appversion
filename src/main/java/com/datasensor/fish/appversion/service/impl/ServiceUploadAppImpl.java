package com.datasensor.fish.appversion.service.impl;

import com.datasensor.fish.appversion.configure.ServiceConfigure;
import com.datasensor.fish.appversion.exception.ServiceException;
import com.datasensor.fish.appversion.model.AppVersionInfo;
import com.datasensor.fish.appversion.model.mapper.AppVersionInfoMapper;
import com.datasensor.fish.appversion.service.api.ServiceUploadApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class ServiceUploadAppImpl implements ServiceUploadApp {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(ServiceUploadAppImpl.class);

    @Autowired
    private AppVersionInfoMapper appVersionInfoMapper;

    @Autowired
    private ServiceConfigure serviceConfigure;

    public boolean uploadAppFile(MultipartFile file, String desc,String version) throws ServiceException {

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
            if (!checkVersion(version)) {
                throw new ServiceException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                // save file to disk
                String storeFilePath = buildStorePath(version);
                Path rootLocation = Paths.get(storeFilePath);
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                logger.info("the file "+filename + " save to " + rootLocation.resolve(filename));
                // save to db
                AppVersionInfo versionInfo = new AppVersionInfo();
                versionInfo.setAppdesc(desc);
                versionInfo.setAppversion(version);
                versionInfo.setDownloadurl(rootLocation.resolve(filename).toString());
                appVersionInfoMapper.insert(versionInfo);
            }
        }
        catch (Exception e) {
            throw new ServiceException("Failed to store file " + filename, e);
        }
        return true;
    }

    private boolean checkVersion(String version) {
        AppVersionInfo appVersionInfo = appVersionInfoMapper.getAppinfoByVersion(version);
        return (appVersionInfo==null) ? true : false;
    }

    // 得到需要保存的路径
    private String buildStorePath(String version) {
        String storeFilePath = serviceConfigure.getStoreFilePath() + "/" + version;
        File dirFile = new File(storeFilePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        return storeFilePath;
    }
}
