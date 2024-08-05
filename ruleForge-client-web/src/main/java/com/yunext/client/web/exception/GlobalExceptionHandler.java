package com.yunext.client.web.exception;

import com.yunext.api.constant.ResultApiCode;
import com.yunext.api.vo.ApiResultVo;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 统一异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value= NullPointerException.class)
    public ResponseEntity<ApiResultVo> handleException(HttpServletRequest request,
                                                       NullPointerException exception)
    {
        logger.error(exception.getMessage(),exception);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApiResultVo fail = new ApiResultVo(ResultApiCode.NOT_NULL);
        return new ResponseEntity(fail, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value= RuntimeException.class)
    public ResponseEntity<ApiResultVo> handleException(HttpServletRequest request,
                                          RuntimeException exception)
    {
        final String message = exception.getMessage();
        logger.error(message,exception);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApiResultVo fail = ApiResultVo.fail(message);
        return new ResponseEntity(fail, headers, HttpStatus.OK);
    }

    @ExceptionHandler(value= Exception.class)
    public ResponseEntity<ApiResultVo> handleException(HttpServletRequest request,
                                          Exception exception)
    {
        logger.error(exception.getMessage(),exception);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ApiResultVo fail = ApiResultVo.fail(exception.getMessage());
        return new ResponseEntity(fail, headers, HttpStatus.OK);
    }



}
