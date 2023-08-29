package com.writingService.writingService.User;

import com.writingService.writingService.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfo join(String username, String password, String email) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setEmail(email);
        userInfo.setPassword(passwordEncoder.encode(password));
        userInfo.setPoint(0);
        this.userRepository.save(userInfo);
        return userInfo;
    }

    public UserInfo getUser(String username) {
        Optional<UserInfo> userInfo = this.userRepository.findByUsername(username);
        if (userInfo.isPresent()) {
            return userInfo.get();
        }
        else {
            throw new DataNotFoundException("User not found.");
        }
    }

    public void pointCharge(String username, int money) {
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);
        userInfo.get().setPoint(userInfo.get().getPoint() + money);
        userRepository.save(userInfo.get());
    }

}
