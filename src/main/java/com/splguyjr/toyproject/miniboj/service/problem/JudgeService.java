package com.splguyjr.toyproject.miniboj.service.problem;

import com.splguyjr.toyproject.miniboj.config.FileProperties;
import com.splguyjr.toyproject.miniboj.exception.WrongSubmitCodeException;
import com.splguyjr.toyproject.miniboj.persistence.problem.ProblemRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class JudgeService {

    //유저의 제출코드를 Solution.java 파일로 생성하는 것은 db레벨에 저장하는 것이 아니라고 판단, Service내에서 처리
    private static final String FILE_PATH = FileProperties.getSolutionPath();
    private static final String CLASS_NAME = "Solution";

    private final ProblemRepository problemRepository;

    public JudgeService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public boolean judge(int problemNum, String submitCode) {
        //1. 코드 저장(Solution.java)파일 생성
        Path javaFilePath = saveCodeToFile(submitCode);

        //2. 컴파일
        if (!compileCode(javaFilePath)) {
            return false;
        }

        //3. 테스트 케이스 로드
        String testCase = problemRepository.loadTestCase(problemNum);
        String answerCase = problemRepository.loadAnswerCase(problemNum);


        //4. 코드 실행 및 결과 비교
        String actualOutput = runCode(testCase);
        System.out.println("answerCase: " + answerCase);
        System.out.println("actualOutput: " + actualOutput);

        if(!answerCase.equals(actualOutput)) {
            throw new WrongSubmitCodeException("틀린 제출 코드입니다.");
        }
        return true;
    }

    private Path saveCodeToFile(String submitCode) {
        Path filePath = Path.of(FILE_PATH + CLASS_NAME + ".java");
        try {
            Files.deleteIfExists(filePath);
            Files.writeString(filePath, submitCode, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Solution 파일 저장 중 오류 발생", e);
        }
        return filePath;
    }

    private boolean compileCode(Path javaFilePath) {
        try {
            //새로운 프로세스 생성하여 자바 컴파일러 프로그램을 통해 해당 경로의 파일을 컴파일
            ProcessBuilder pb = new ProcessBuilder("javac", javaFilePath.toString());
            Process process = pb.start();

            //컴파일 프로세스 완료될 때까지 현재 스레드 대기
            process.waitFor();
            return process.exitValue() == 0; // 0이면 성공
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("컴파일 중 오류 발생", e); // 컴파일 오류 처리
        }
    }

    private String runCode(String input) {
        // 이중 try문, 해당 블록 내에서 예외 처리되지 않으면 상위 블록으로 전달
        try {
            System.out.println("채점 시작...");
            Thread.sleep(1000); // 1초 대기

            // java -cp 명령어를 통해 앞서 컴파일된 .class 파일 실행
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", FILE_PATH, CLASS_NAME);
            Process process = pb.start();

            // 입력값 전달 및 실행 결과 읽기
            StringBuilder output = new StringBuilder();
            try (
                    // 프로세스에 데이터를 전달하기 위한 출력 스트림, 프로세스의 표준 입력(stdin) 스트림으로 출력
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    // 프로세스에서 출력된 데이터를 읽어오기 위한 입력 스트림, 프로세스의 표준 입력(stdout) 스트림의 내용을 읽어옴
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
            ) {
                // 입력값 전달
                System.out.println("입력값 전달 중...");
                Thread.sleep(1000); // 1초 대기
                writer.write(input);
                writer.flush();

                // 실행 결과 읽기
                System.out.println("실행 결과 읽는 중...");
                Thread.sleep(1000); // 1초 대기
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }

            // 실행 중인 프로세스가 끝날 때까지 대기
            process.waitFor();
            System.out.println("채점 완료.");
            Thread.sleep(1000); // 1초 대기
            return output.toString().trim(); // 결과 반환
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("코드 실행 중 오류 발생", e); // 실행 중 오류 처리
        }
    }

}
