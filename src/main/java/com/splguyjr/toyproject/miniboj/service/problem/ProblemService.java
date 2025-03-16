package com.splguyjr.toyproject.miniboj.service.problem;

import com.splguyjr.toyproject.miniboj.persistence.problem.ProblemRepository;
import com.splguyjr.toyproject.miniboj.persistence.problem.checker.PathChecker;

//문제 테스트 케이스
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final PathChecker pathChecker;

    public ProblemService(ProblemRepository problemRepository, PathChecker pathChecker) {
        this.problemRepository = problemRepository;
        this.pathChecker = pathChecker;
    }

    //문제 번호에 매핑되는 테스트 케이스을 저장
    public void addtestCase(int problemNum, String testCase) {
        problemRepository.insertTestCase(problemNum, testCase);
    }

    //문제 번호에 매핑되는 테스트 케이스를 실행했을 때 나와야 할 정답 케이스를 저장
    public void addAnswerCase(int problemNum, String answer) {
        problemRepository.insertAnswerCase(problemNum, answer);
    }

    //문제 번호에 해당하는 테스트 케이스, 정답 케이스가 있는지 확인
    public boolean isRegisteredProblem(int problemNum) {
        return pathChecker.exists(problemNum);
    }
}
