package com.xhc.sbm.exception;

import com.xhc.sbm.bean.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/4/1 15:54
 * @Description:
 */
@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseResult handlerGlobalException(Exception e,
                                                 HttpServletRequest request, HttpServletResponse response){
        log.error("捕获全局错误", e);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return ResponseResult.error("系统错误");
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseResult handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e,
                                                                HttpServletRequest request, HttpServletResponse response){
        String message = String.format("上传文件大小不符合限制，最大：1MB");
        log.error(message);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return ResponseResult.error(message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult hanlderParamException(MethodArgumentNotValidException e,
                                                HttpServletRequest request, HttpServletResponse response){
        log.error("参数验证失败", e);
        response.setHeader("Access-Control-Allow-Origin", "*");
        StringBuilder sb = new StringBuilder("参数验证失败。");
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for(ObjectError error : allErrors){
            sb.append(error.getDefaultMessage()).append("。");
        }
        return ResponseResult.error(sb.toString());
    }

    @ExceptionHandler(SbmException.class)
    public ResponseResult handlerSbmException(SbmException e,
                                              HttpServletRequest request, HttpServletResponse response){
        log.error("捕获sbm异常", e);
        response.setHeader("Access-Control-Allow-Origin", "*");
        return ResponseResult.error(e.getErrorCode(), e.getErrorMessage());
    }
}
