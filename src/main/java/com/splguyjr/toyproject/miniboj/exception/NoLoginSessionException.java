package com.splguyjr.toyproject.miniboj.exception;

//로그인 세션 존재하지 않는 상태에서 로그아웃 시도 시 발생하는 예외
public class NoLoginSessionException extends RuntimeException{
    public NoLoginSessionException(String message) { super(message); }
}
