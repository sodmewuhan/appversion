package com.datasensor.fish.appversion.controller;

import com.datasensor.fish.appversion.service.api.ServiceUploadApp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class Upload {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(Upload.class);

    @Autowired
    private ServiceUploadApp serviceUploadApp;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("desc") String desc,
                                   @RequestParam("appversion") String appversion,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择一个你要上传的app文件");
            return "redirect:uploadStatus";
        }

        if (StringUtils.isEmpty(appversion) || StringUtils.isEmpty(desc)) {
            redirectAttributes.addFlashAttribute("message", "请填写完整版本号或者版本说明");
            return "redirect:uploadStatus";
        }

        try {
            boolean fileUploadResult = serviceUploadApp.uploadAppFile(file,desc);
            if (fileUploadResult) {
                redirectAttributes.addFlashAttribute("message",
                        "你的文件上传失败 '" + file.getOriginalFilename() + "'");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "你的文件上传成功 '" + file.getOriginalFilename() + "'");
            }
        } catch (Exception e) {
            logger.error("file upload is fail,the error is " ,e);
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
