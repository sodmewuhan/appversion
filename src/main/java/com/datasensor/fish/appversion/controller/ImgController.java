package com.datasensor.fish.appversion.controller;

import com.datasensor.fish.appversion.service.api.ImgHeadfaceService;
import com.datasensor.fish.appversion.utils.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 图片处理控制类
 */
@Controller
public class ImgController {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger LOGGER = LoggerFactory.getLogger(ImgController.class);

    @Autowired
    private ImgHeadfaceService imgHeadfaceService; // 用户头像处理类

    /**
     * 用户头像上传
     * @param file  用户头像文件
     * @param username 用户名称
     * @return
     */
    @PostMapping(value="/headfaceUpload")
    public String headfaceUpload(@RequestParam("fileName") MultipartFile file,
                                 @RequestParam("username") String username,
                                 RedirectAttributes redirectAttributes) {

        ResultGenerator resultGenerator = new ResultGenerator();
        try {
            imgHeadfaceService.headfaceUpload(file,username);
//            return resultGenerator.genSuccessResult();
            redirectAttributes.addFlashAttribute("message", "文件上传成功");
            return "redirect:imageUploadStatus";
        } catch (Exception e) {
            LOGGER.error("upload the headface file is error " + e.getMessage(),e);
            redirectAttributes.addFlashAttribute("message", "文件上传失败");
            return "redirect:imageUploadStatus";
        }
    }

    @GetMapping("/imageUploadStatus")
    public String uploadStatus() {
        return "imageUploadStatus";
    }
}
