package com.datasensor.fish.appversion.service.api;

import com.datasensor.fish.appversion.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传及下载功能
 */
public interface ServiceUploadApp {

    public boolean uploadAppFile(MultipartFile file, String desc,String version) throws ServiceException;

}
