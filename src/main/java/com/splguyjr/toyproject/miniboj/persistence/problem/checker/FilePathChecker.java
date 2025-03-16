package com.splguyjr.toyproject.miniboj.persistence.problem.checker;

import com.splguyjr.toyproject.miniboj.config.FileProperties;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathChecker implements PathChecker {

    private static final String TESTCASE_FILE_PATH = FileProperties.getTestCasePath();
    private static final String ANSWER_FILE_PATH = FileProperties.getAnswerCasePath();
    private static final String FILE_EXTENSION = FileProperties.getFileExtension();

    //테스트 케이스, 정답 케이스가 해당 디렉토리내 모두 존재할 시 true 반환
    @Override
    public boolean exists(int problemNum) {
        // 파일 경로에 번호를 포함시켜서 경로 생성
        Path testcasePath = Paths.get(TESTCASE_FILE_PATH + problemNum + FILE_EXTENSION);
        Path answerPath = Paths.get(ANSWER_FILE_PATH + problemNum + FILE_EXTENSION);

        return pathExists(testcasePath) && pathExists(answerPath);
    }

    //하나의 Path가 존재하는지 확인
    private boolean pathExists(Path path) {
        return Files.exists(path);
    }
}
