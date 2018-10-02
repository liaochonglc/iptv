package com.ido.iptv.controller;

import com.ido.iptv.entity.dto.ReturnBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Controller层全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(getClass());


    /**
     * Http方法异常，目前全局使用POST
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ReturnBean httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage(), e);

        //用户使用的方法
        String method = e.getMethod();
        String supportMethods = "";

        //目前系统支持的方法
        String[] methods = e.getSupportedMethods();

        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                if (i == methods.length - 1) {
                    supportMethods = supportMethods.concat(methods[i]);
                } else {
                    supportMethods = supportMethods.concat(methods[i] + ",");
                }
            }
        }

        return ReturnBean.failure("请求方法: " + method + " 不被支持，当前请求支持的方法如下: " + supportMethods);
    }

    /**
     * 数据校检异常
     *
     * @param e
     * @return ReturnBean
     */
    @ExceptionHandler(BindException.class)
    public ReturnBean validExceptionHandle(BindException e) {
        log.info(e.getMessage(), e);

        String msg = getMessage(e);

        return ReturnBean.failure(msg);
    }

    /**
     * 数据校检异常,当参数含有注解 {@code @RequestBody} 时,
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor} 抛出的异常
     * {@link MethodArgumentNotValidException}
     *
     * @param e
     * @return ReturnBean
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ReturnBean methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.info(e.getMessage(), e);

        String msg = getMessage(e);

        return ReturnBean.failure(msg);
    }

    /**
     * 请求参数丢失异常，当方法标记为 <code>@RequestParam(require = true)</code>
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ReturnBean missingServletRequestParameterExceptionHandle(MissingServletRequestParameterException e) {
        log.info(e.getMessage(), e);

        return ReturnBean.failure("请求参数丢失");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ReturnBean accessDeniedExceptionHandle(AccessDeniedException e) {
        log.info(e.getMessage(), e);

        return ReturnBean.failure("用户权限不足");
    }

    /**
     * 未知异常捕获
     *
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ReturnBean throwableHandle(Throwable e) {
        String msg = e.getMessage();

        log.error(msg, e);
        return StringUtils.hasText(msg) ? ReturnBean.error(msg) :
                ReturnBean.error("服务器异常");
    }

    /**
     * 异常消息获取
     *
     * @param e
     * @return
     */
    private String getMessage(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;

            String message = "";
            List<FieldError> fieldErrors = validException.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;

            String message = "";
            List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else {
            log.error(e.getMessage(), e);
            return "异常类型无法捕获";
        }
    }
}
