package com.example.kbfinal.service;

import com.example.kbfinal.entity.User;
import com.example.kbfinal.repository.UserRepository;
import com.example.kbfinal.config.SecurityConfig.Base64PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(User user) {
        // 비밀번호를 암호화하여 저장
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // password를 인코딩
        // user entity에 인코딩 된 password를 넣기
        userRepository.save(user);
    }

   public boolean authenticate(String username, String password) {
       // 사용자 조회
       User user = userRepository.findByUsername(username); // 직접 repo에서 구현
       if (user == null) {
           return false;
       }
       // 입력된 비밀번호와 저장된 암호화된 비밀번호를 비교
       return passwordEncoder.matches(password, user.getPassword());

   }

    // 이후 컨트롤러에서 들어오게 될  내용 추가 구현하기
    // user 정보를 삭제하는 메서드
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // user 정보를 수정하는 메서드
    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setAddress(userDetails.getAddress());
            user.setPhoneNumber(userDetails.getPhoneNumber());
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            return userRepository.save(user);
        }
        return null;
    }

    // 전체 user List를 조회하는 메서드
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 전체 user의 숫자를 조회하는 메서드
    public Long getUserCount() {
        return userRepository.count();
    }
}
