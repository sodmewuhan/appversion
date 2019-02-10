package com.datasensor.fish.appversion.service.api;

import org.springframework.web.multipart.MultipartFile;

public interface ImgHeadfaceService {

    public void headfaceUpload(MultipartFile file,String username) throws Exception;

}
