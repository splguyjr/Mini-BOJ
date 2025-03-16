package com.splguyjr.toyproject.miniboj.service.user;

import com.splguyjr.toyproject.miniboj.domain.User;
import com.splguyjr.toyproject.miniboj.exception.ExistingLoginSessionException;
import com.splguyjr.toyproject.miniboj.exception.NoLoginSessionException;
import com.splguyjr.toyproject.miniboj.persistence.user.UserRepository;

import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    private SessionManager sessionManager = null;   //더 나은 해결 방법을 찾지 못해 null로 초기값 할당

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) throws ExistingLoginSessionException {
        if (isDuplicateUserId(user.getId())) {
            throw new ExistingLoginSessionException("이미 존재하는 아이디입니다.");
        }
        userRepository.insertUser(user);
    }

    public void loginUser(String userId, String pwd) {
        //로그인 세션이 이미 존재할 시 에러
        if(SessionManager.checkExistingSession()) {
            throw new ExistingLoginSessionException("이미 로그인 세션이 존재합니다.");
        }

        //user의 아이디를 통해 존재 여부를 확인, 없으면 아이디 없음 에러
        User user = userRepository.selectUserById(userId);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }

        //있는데 비밀번호가 다르면 비밀번호 틀림 에러
        if (!user.getPwd().equals(pwd)) {
            throw new IllegalArgumentException("적절하지 않은 아이디와 비밀번호의 조합입니다.");
        }

        //로그인 성공시 해당 유저에 대한 로그인 세션 생성
        sessionManager = SessionManager.getInstance(user);
    }

    public void logoutUser() {
        if (!SessionManager.checkExistingSession()) {
            throw new NoLoginSessionException("로그인 세션이 존재하지 않습니다.");
        }

        sessionManager.resetSession();//세션 인스턴스 null로 초기화
    }

    public void deleteUser() {
        if (!SessionManager.checkExistingSession()) {
            throw new NoLoginSessionException("현재 로그인 세션이 존재하지 않습니다.");
        }

        User deleteUser = sessionManager.getUser();
        userRepository.deleteUser(deleteUser);
        sessionManager.resetSession();
    }

    public String selectAllUsers() {
        return userRepository.selectAllUsers().stream()
                .map(User::getId)
                .collect(Collectors.joining(", ", "[", "]"));

    }
    private boolean isDuplicateUserId(String userId) {
        return userRepository.selectAllUsers()
                .stream()
                .anyMatch(user -> user.getId().equals(userId));
    }


}
