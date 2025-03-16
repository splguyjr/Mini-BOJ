package com.splguyjr.toyproject.miniboj.exception;

//제출된 코드가 테스트 케이스를 입력으로 받아 정답 케이스와 비교했을 때, 다른 출력을 반환할 때 발생하는 예외
public class WrongSubmitCodeException extends RuntimeException{
    public WrongSubmitCodeException(String message) { super(message); }
}
