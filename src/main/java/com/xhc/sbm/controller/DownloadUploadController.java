package com.xhc.sbm.controller;

import com.xhc.sbm.bean.ResponseResult;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: xhc
 * @Date: 2020/4/2 12:08
 * @Description:
 */
@RestController
@Log4j2
@Api(tags = "上传下载")
public class DownloadUploadController {

    @Value("${app.fileUpload.filePath:d:/temp}")
    private String filePath;

    @PostMapping(value = "/uploadFile")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest servletRequest){
        String fileName = multipartFile.getOriginalFilename();

        try {
//            File catalog = new File(servletRequest.getServletContext().getRealPath("/file"));
            File catalog = new File(filePath);
            if(!catalog.exists()){
                catalog.mkdirs();
            }
            File file = new File(filePath , fileName);
            multipartFile.transferTo(file);
            return ResponseResult.success();
        } catch (IllegalStateException | IOException e ) {
            e.printStackTrace();
            log.error("上传失败，{}", e);
            return ResponseResult.error("上传失败，" + e.getMessage());
        }
    }


    @PostMapping("download")
    public void download(@RequestParam(value = "fileName") String fillName, HttpServletResponse response) throws FileNotFoundException {
        File file = new File(filePath + File.separator + fillName);
        FileInputStream fileInputStream = new FileInputStream(file);
        // 设置被下载而不是被打开
        response.setContentType("application/gorce-download");
        // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
        response.addHeader("Content-disposition", "attachment;fileName=" + fillName);
        try {
            OutputStream outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
