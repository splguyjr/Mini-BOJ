package com.splguyjr.toyproject.miniboj.exception;

//등록되지 않는 문제를 풀려고 시도할 때 발생하는 예외
public class UnregisteredProblemException extends RuntimeException{
    public UnregisteredProblemException(String message) { super(message); }
}
