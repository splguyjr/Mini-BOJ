package com.splguyjr.toyproject.miniboj.persistence.problem.answer;

import com.splguyjr.toyproject.miniboj.config.FileProperties;

import java.io.*;

//정답 케이스 저장 및 로드
public class FileAnswerStorage implements AnswerStorage{
    //정답 저장 경로
    private static final String FILE_PATH = FileProperties.getAnswerCasePath();
    private static final String FILE_EXTENSION = FileProperties.getFileExtension();

    //정답 케이스 저장
    @Override
    public void save(int problemNum, String testCase) {
        String fileName = FILE_PATH + problemNum + FILE_EXTENSION;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(testCase);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 중 오류 발생", e);
        }
    }

    //정답 케이스 로드
    @Override
    public String load(int problemNum) {
        StringBuilder problemContent = new StringBuilder();
        String fileName = FILE_PATH + problemNum + FILE_EXTENSION;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                problemContent.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 중 오류 발생", e);
        }

        return problemContent.toString().trim();
    }
}
