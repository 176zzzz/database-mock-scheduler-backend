package com.zp.config;

import com.zp.common.exception.ServiceException;
import com.zp.model.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler
 *
 * @author ZP
 * 
 */
@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseResult handleServiceException(ServiceException e, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String serviceExceptionMsg = "请求发生业务异常，请求地址：{}，报错为：{}";
        log.error(serviceExceptionMsg,requestUrl,e.getMessage());
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUrl, e);
        return ResponseResult.fail(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUrl, e);
        return ResponseResult.fail(e.getMessage());
    }
}
