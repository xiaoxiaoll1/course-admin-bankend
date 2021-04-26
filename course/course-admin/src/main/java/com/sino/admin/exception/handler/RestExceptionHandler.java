package com.sino.admin.exception.handler;

import dto.result.ResultDataDto;
import excetion.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局错误处理
 * @author xiaozj
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResultDataDto handleBusinessException(BusinessException ex){
        log.error(ex.getMessage(),ex);
        return ResultDataDto.newException(ex);
    }
}
