package com.splguyjr.toyproject.miniboj.persistence.problem.testcase;

public interface TestCaseStorage {
    void save(int problemNum, String testCase);
    String load(int problemNum);
}
