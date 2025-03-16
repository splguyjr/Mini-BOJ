package com.splguyjr.toyproject.miniboj.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//파일 경로, 확장자 정보를 별도의 config 파일을 통해 관리
public class FileProperties {
    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = FileProperties.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("파일 설정 정보를 불러오는데 실패하였습니다:", e);
        }
    }

    public static String getUserDBPath() {
        return properties.getProperty("userdb.path");
    }

    public static String getTestCasePath() {
        return properties.getProperty("testcase.path");
    }

    public static String getAnswerCasePath() {
        return properties.getProperty("answercase.path");
    }

    public static String getFileExtension() {
        return properties.getProperty("file.extension");
    }

    public static String getSolutionPath() {
        return properties.getProperty("solution.path");
    }
}
