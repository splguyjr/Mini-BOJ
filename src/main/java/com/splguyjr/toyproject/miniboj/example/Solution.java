package com.splguyjr.toyproject.miniboj.example;

//문제 코드 제출 시 복사하기 용도 -> 삭제해도 무방한 클래스

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int n = 0;
        while(t-- > 0) {
           n = Math.max(n, sc.nextInt());
        }
        System.out.println(n);
    }
}
