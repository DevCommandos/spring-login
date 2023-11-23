package dev.commandos.login.domain.login;

import dev.commandos.login.domain.member.Member;
import dev.commandos.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(Login login) {
        return memberRepository.findByLoginId(login.getLoginId())
                .filter(m -> m.getPassword().equals(login.getPassword()))
                .orElse(null);
    }

}
