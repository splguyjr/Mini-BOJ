package com.splguyjr.toyproject.miniboj.persistence.problem.testcase;

import com.splguyjr.toyproject.miniboj.config.FileProperties;

import java.io.*;

//입력 받은 문제 번호와 테스트 케이스를 파일 형태로 저장
public class FileTestCaseStorage implements TestCaseStorage{
    //테스트 케이스 저장용
    private static final String FILE_PATH = FileProperties.getTestCasePath();
    private static final String FILE_EXTENSION = FileProperties.getFileExtension();

    //지정된 위치에 지정된 이름으로 테스트 케이스 파일 저장
    @Override
    public void save(int problemNum, String testCase) {
        String fileName = FILE_PATH + problemNum + FILE_EXTENSION;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
             bw.write(testCase);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }

    }

    //테스트 케이스 내용을 불러와 String 형태로 반환
    @Override
    public String load(int problemNum) {
        StringBuilder problemContent = new StringBuilder(); // 파일 내용을 누적할 StringBuilder
        String fileName = FILE_PATH + problemNum + FILE_EXTENSION; // 파일 경로

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                problemContent.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 중 오류 발생", e);
        }

        return problemContent.toString();
     }
}

