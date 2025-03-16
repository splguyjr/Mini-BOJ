package com.splguyjr.toyproject.miniboj.persistence.problem;

import com.splguyjr.toyproject.miniboj.persistence.problem.answer.AnswerStorage;
import com.splguyjr.toyproject.miniboj.persistence.problem.testcase.TestCaseStorage;

public class ProblemRepository {
    private final TestCaseStorage testCaseStorage;
    private final AnswerStorage answerStorage;

    public ProblemRepository(TestCaseStorage testCaseStorage, AnswerStorage answerStorage) {
        this.testCaseStorage = testCaseStorage;
        this.answerStorage =  answerStorage;
    }

    //테스트 케이스 저장
    public void insertTestCase(int problemNum, String testCase) {
        testCaseStorage.save(problemNum, testCase);
    }

    //테스트 케이스 불러옴
    public String loadTestCase(int problemNum) {
        return testCaseStorage.load(problemNum);
    }

    //정답 케이스 저장
    public void insertAnswerCase(int problemNum, String testCase) {
        answerStorage.save(problemNum, testCase);
    }

    //정답 케이스 불러옴
    public String loadAnswerCase(int problemNum) {
        return answerStorage.load(problemNum);
    }


}
