package com.splguyjr.toyproject.miniboj.exception;

//로그인 세션 존재 상태에서 로그인 시도 시 발생하는 예외
public class ExistingLoginSessionException extends RuntimeException{
    public ExistingLoginSessionException(String message) { super(message); }
}
