package com.example.demo.service;

import com.example.demo.exception.impl.AlreadyExistUserException;
import com.example.demo.exception.impl.InvalidPasswordException;
import com.example.demo.model.Auth;
import com.example.demo.persist.MemberRepository;
import com.example.demo.persist.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    public MemberEntity register(Auth.SignUp member) {
        // 아이디가 존재하는 경우 exception 발생
        boolean exists = this.memberRepository.existsByUsername(member.getUsername());
        if (exists) {
            throw new AlreadyExistUserException();
        }

        // 비밀번호 암호화
        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        
        // 회원 정보 저장
        return this.memberRepository.save(member.toEntity());
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        // id로 멤버 조회
        MemberEntity user = this.memberRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + member.getUsername()));

        // 패스워드 일치 여부 확인
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return user;
    }
}