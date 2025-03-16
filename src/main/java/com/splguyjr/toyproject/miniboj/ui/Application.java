package com.splguyjr.toyproject.miniboj.ui;

import com.splguyjr.toyproject.miniboj.domain.User;
import com.splguyjr.toyproject.miniboj.exception.ExistingLoginSessionException;
import com.splguyjr.toyproject.miniboj.exception.NoLoginSessionException;
import com.splguyjr.toyproject.miniboj.exception.UnregisteredProblemException;
import com.splguyjr.toyproject.miniboj.exception.WrongSubmitCodeException;
import com.splguyjr.toyproject.miniboj.persistence.problem.answer.FileAnswerStorage;
import com.splguyjr.toyproject.miniboj.persistence.problem.checker.FilePathChecker;
import com.splguyjr.toyproject.miniboj.persistence.problem.testcase.FileTestCaseStorage;
import com.splguyjr.toyproject.miniboj.persistence.problem.ProblemRepository;
import com.splguyjr.toyproject.miniboj.persistence.user.FileUserStorage;
import com.splguyjr.toyproject.miniboj.persistence.user.UserRepository;
import com.splguyjr.toyproject.miniboj.service.problem.JudgeService;
import com.splguyjr.toyproject.miniboj.service.problem.ProblemService;
import com.splguyjr.toyproject.miniboj.service.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Application {
    private final Scanner scanner;
    private final UserService userService;
    private final ProblemService problemService;
    private final JudgeService judgeService;

    public Application() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService(new UserRepository(new FileUserStorage()));
        ProblemRepository problemRepository = new ProblemRepository(new FileTestCaseStorage(), new FileAnswerStorage());
        this.problemService = new ProblemService(problemRepository, new FilePathChecker());
        this.judgeService = new JudgeService(problemRepository);
    }

    public void run() {
        while (true) {
            System.out.println("\n===== 회원 관리 프로그램 =====");
            System.out.println("1. 로그인");
            System.out.println("2. 로그아웃");
            System.out.println("3. 회원 가입");
            System.out.println("4. 회원 탈퇴");
            System.out.println("5. 전체 회원 조회");
            System.out.println("6. 문제 번호 및 테스트 케이스 등록");
            System.out.println("7. 문제 번호 및 정답 케이스 등록");
            System.out.println("8. 문제 번호 및 코드 제출하여 채점");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> logout();
                    case 3 -> registerUser();
                    case 4 -> deleteUser();
                    case 5 -> printAllUser();
                    case 6 -> addProblem();
                    case 7 -> addAnswer();
                    case 8 -> solveProblem();
                    case 9 -> {
                        System.out.println("프로그램을 종료합니다.");
                        return;
                    }
                    default -> System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류: " + e.getMessage());
            }
        }
    }

    private void registerUser() {
        try {
            System.out.print("아이디 입력: ");
            String userId = scanner.nextLine();

            System.out.print("비밀번호 입력: ");
            String pwd = scanner.nextLine();

            User newUser = new User(userId, pwd);

            userService.registerUser(newUser);
            System.out.println("회원 가입 성공: " + userId);

        } catch (ExistingLoginSessionException e) {
            System.out.println("회원 가입 실패: " + e.getMessage());
        }
    }

    private void deleteUser() {
        try {
            System.out.print("정말로 탈퇴하시겠습니까 (Y/N 입력) : ");
            char input = scanner.nextLine().trim().charAt(0);

            if (input == 'Y' || input == 'y') {
                userService.deleteUser();
                System.out.println("회원 탈퇴 완료");
            } else if (input == 'N' || input == 'n') {
                System.out.println("회원 탈퇴를 취소하였습니다");
            } else System.out.println("올바르지 않은 입력입니다");
        } catch (NoLoginSessionException e) {
            System.out.println("회원 탈퇴 실패: " + e.getMessage());
        }
    }

    private void login() {
        try {
            System.out.print("아이디 입력: ");
            String userId = scanner.nextLine();

            System.out.print("비밀번호 입력: ");
            String pwd = scanner.nextLine();

            userService.loginUser(userId, pwd);
            System.out.println("로그인 성공: " + userId);
        } catch (ExistingLoginSessionException | IllegalArgumentException e) {
            System.out.println("로그인 실패: " + e.getMessage());
        }
    }

    private void logout() {
        try {
            System.out.print("로그아웃 하시겠습니까 (Y/N 입력) : ");
            char input = scanner.nextLine().trim().charAt(0);

            if (input == 'Y' || input == 'y') {
                userService.logoutUser();
                System.out.println("로그아웃 성공");
            } else if (input == 'N' || input == 'n') {
                System.out.println("로그아웃을 취소하였습니다");
            } else System.out.println("올바르지 않은 입력입니다");
        } catch (NoLoginSessionException e) {
            System.out.println("로그아웃 실패: " + e.getMessage());
        }
    }

    private void printAllUser() {
        System.out.println(userService.selectAllUsers());
    }

    private void addProblem() {
        try {
            System.out.print("등록하고자 하는 문제의 번호를 입력하세요 : ");
            int problemNum = scanner.nextInt();
            scanner.nextLine();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder sb = new StringBuilder();

            System.out.print("등록하고자 하는 테스트 케이스를 입력하세요 (빈 줄로 입력 종료): ");
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) { // 빈 줄 입력까지 계속 입력 받음
                sb.append(line).append("\n"); // 각 줄을 StringBuilder에 추가
            }
            String testCase = sb.toString().stripTrailing();
            problemService.addtestCase(problemNum, testCase);
            System.out.println("정상적으로 등록되었습니다.");
        } catch (RuntimeException | IOException e) {
            System.out.println("테스트 케이스 등록 실패 : " + e.getMessage());
        }
    }

    private void addAnswer() {
        try {
            System.out.print("등록하고자 하는 문제의 번호를 입력하세요 : ");
            int problemNum = scanner.nextInt();
            scanner.nextLine();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder sb = new StringBuilder();

            System.out.print("정답 케이스를 입력하세요 : (빈 줄로 입력 종료): ");
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                sb.append(line).append("\n");
            }
            String answerCase = sb.toString().stripTrailing();

            problemService.addAnswerCase(problemNum, answerCase);
            System.out.println("정상적으로 등록되었습니다.");
        } catch (RuntimeException | IOException e) {
            System.out.println("정답 케이스 등록 실패 : " + e.getMessage());
        }
    }

    private void solveProblem() {
        try {
            System.out.print("풀고자 하는 문제의 번호를 입력하세요 : ");
            int problemNum = scanner.nextInt();
            scanner.nextLine();

            boolean isRegistered = problemService.isRegisteredProblem(problemNum);
            if (!isRegistered) {
                throw new UnregisteredProblemException("등록되지 않은 문제입니다.");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringBuilder sb = new StringBuilder();

            System.out.println("제출 코드를 입력하세요 (END 입력 시 종료):");
            String line;

            while (!(line = br.readLine()).equalsIgnoreCase("END")) { // "END" 또는 "end" 입력 시 종료
                sb.append(line).append("\n");
            }

            String submitCode = sb.toString();

            if(judgeService.judge(problemNum,  submitCode)) {
                System.out.println("채점 결과 : 정답입니다!");
            }
        } catch (UnregisteredProblemException e) {
            System.out.println("채점 오류 : " + e.getMessage());
        } catch (WrongSubmitCodeException e) {
            System.out.println("채점 결과 : " + e.getMessage());
        }
        catch (RuntimeException | IOException e) {
            System.out.println("채점 과정 오류 발생 : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Application().run();
    }
}
