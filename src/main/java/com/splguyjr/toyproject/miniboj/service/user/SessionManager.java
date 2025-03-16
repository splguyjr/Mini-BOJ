package com.splguyjr.toyproject.miniboj.service.user;

import com.splguyjr.toyproject.miniboj.domain.User;

//로그인 세션을 싱글톤으로 관리하는 클래스
public class SessionManager {
    private static SessionManager sessionInstance;
    private User user;

    private SessionManager(User user) {
        this.user = user;
    }

    public static SessionManager getInstance(User user) {
        if (sessionInstance == null) {
            sessionInstance = new SessionManager(user);
        }

        return sessionInstance;
    }

    public User getUser() {
        return user;
    }

    public static void resetSession() {
        sessionInstance = null;
    }

    public static boolean checkExistingSession() {
        //세션이 존재하면 true 반환
        return sessionInstance != null;
    }

}
