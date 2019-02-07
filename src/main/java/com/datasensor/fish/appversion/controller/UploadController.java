package com.datasensor.fish.appversion.controller;

import com.datasensor.fish.appversion.model.AppVersionInfo;
import com.datasensor.fish.appversion.service.api.AppVersionManage;
import com.datasensor.fish.appversion.service.api.ServiceUploadApp;
import com.datasensor.fish.appversion.utils.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class UploadController {

    //定义一个全局的记录器，通过LoggerFactory获取
    private final static Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private ServiceUploadApp serviceUploadApp;

    @Autowired
    private AppVersionManage appVersionManage;

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
            boolean fileUploadResult = serviceUploadApp.uploadAppFile(file,desc,appversion);
            if (fileUploadResult) {
                redirectAttributes.addFlashAttribute("message",
                        "你的文件上传成功 '" + file.getOriginalFilename() + "'");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "你的文件上传失败 '" + file.getOriginalFilename() + "'");
            }
        } catch (Exception e) {
            logger.error("file upload is fail,the error is " ,e);
        }
        return "redirect:uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * 下载版本
     * @param version
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/downloadByVersion")
    public void downloadByVersion(@RequestParam("version") String version,
                                    HttpServletRequest request, HttpServletResponse response) {
        logger.info("call the function downloadByVersion ,the parameter version is " + version);
        ResultGenerator resultGenerator = new ResultGenerator();
        if (StringUtils.isEmpty(version)) {
            logger.error("the parameter is empty");
        }

        AppVersionInfo appVersionInfo = appVersionManage.getAppinfoByVersion(version);
        if (appVersionInfo!= null && !StringUtils.isEmpty(appVersionInfo.getDownloadurl())) {
            String realPath = appVersionInfo.getDownloadurl();
            File file = new File(realPath);
            if (file.exists()) {
                downloadFileByVersion(file,request,response);
            } else {
                logger.error("the file is not exist");
            }
        }
    }

    @GetMapping("/downloadLast")
    public void downloadLast(HttpServletRequest request, HttpServletResponse response) {
        AppVersionInfo appVersionInfo = appVersionManage.getLastVersion();
        if (appVersionInfo!= null && !StringUtils.isEmpty(appVersionInfo.getDownloadurl())) {
            String realPath = appVersionInfo.getDownloadurl();
            File file = new File(realPath);
            if (file.exists()) {
                downloadFileByVersion(file,request,response);
            } else {
                logger.error("the file is not exist");
            }
        }
    }

    // 根据版本下载
    private void downloadFileByVersion(File file, HttpServletRequest request, HttpServletResponse response) {
        String fileName = file.getName();
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("success");
        } catch (Exception e){
            logger.error("the download file occur the errro ,the message is " + e.getMessage(),
                    e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("the download file occur the errro ,the message is " + e.getMessage(),
                            e);
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    logger.error("the download file occur the errro ,the message is " + e.getMessage(),
                            e);
                }
            }
        }
    }
}
