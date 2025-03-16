package com.splguyjr.toyproject.miniboj.persistence.problem.answer;

public interface AnswerStorage {
    void save(int problemNum, String testCase);
    String load(int problemNum);
}
