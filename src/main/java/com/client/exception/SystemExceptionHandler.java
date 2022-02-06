/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author leonard
 */
@ControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SystemException.class)
    public ResponseEntity SysExceptionHandler(HttpServletRequest req, Exception e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
