package com.techsoft.springsecurity.error;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(Exception.class)
    public ProblemDetail Customerror(Exception ex){
        ProblemDetail problemDetail=null;
    if (ex instanceof BadCredentialsException){
         problemDetail=ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(401), ex.getMessage());
        problemDetail.setProperty("reason","Bad credantials");
    } else if (ex instanceof AccessDeniedException) {
        problemDetail=ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(403), ex.getMessage());
        problemDetail.setProperty("reason","not auhthorized");
    } else if (ex instanceof UsernameNotFoundException) {
        problemDetail=ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(403), ex.getMessage());
        problemDetail.setProperty("reason","user name not found");
    }else if (ex instanceof ExpiredJwtException) {
        problemDetail=ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(403), ex.getMessage());
        problemDetail.setProperty("reason","JWT token expired");
    }

        return problemDetail;
    }
//
    @ExceptionHandler(LockedException.class)
    public ProblemDetail sign(LockedException ex){
        ProblemDetail problemDetail=ProblemDetail.forStatusAndDetail(
                HttpStatusCode.valueOf(403), ex.getMessage());
        problemDetail.setProperty("reason","JWT signature does not match locally computed signature");

        return problemDetail;
    }
}
