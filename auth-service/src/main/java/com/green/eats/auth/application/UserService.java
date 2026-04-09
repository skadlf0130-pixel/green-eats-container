package com.green.eats.auth.application;

import com.green.eats.auth.application.model.UserSignupReq;
import com.green.eats.auth.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // DI 받는것. 받을수 있다는 건 스프링이 저기에 객체를 생성해서 빈등록을
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignupReq req){
        //회원가입 시켜주세요.
        User newUser = new User();
        newUser.setEmail(req.getEmail());
        newUser.setPassword(req.getPassword());
        newUser.setName(req.getName());
        newUser.setAddress(req.getAddress());

        userRepository.save(newUser);
    }

    public User signin(UserSignupReq req){
        User signrdUser = userRepository.findByEmail(req.getEmail() );
        log.info("signrdUser: {}", signrdUser);
        if(signrdUser == null || !passwordEncoder.matches(req.getPassword(), signrdUser.getPassword()  )){
            notFoundUser();
        }
        return signrdUser;
    }
    private void notFoundUser(){
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"아이디, 비밀번호를 확인해주세요.");
    }
}

