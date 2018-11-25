package com.datasensor.fish.appversion.service.api;

import com.datasensor.fish.appversion.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;

public interface ServiceUploadApp {

    public boolean uploadAppFile(MultipartFile file, String desc) throws ServiceException;

}
