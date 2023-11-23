package dev.commandos.login.web.controller;

import dev.commandos.login.domain.login.Login;
import dev.commandos.login.domain.login.LoginService;
import dev.commandos.login.domain.member.Member;
import dev.commandos.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginSession", required = false) Member loginMember) {
        if (loginMember == null) {
            return "loginForm";
        }
        return "wellcome";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, @Validated Login login) {
        log.info("Login : {}", login);
        Member loginMember = loginService.login(login);
        if(ObjectUtils.isEmpty(loginMember)){
            return "fail";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginSession", loginMember);
        return "success";
    }

    @ResponseBody
    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "세션이 없습니다.";
        }
        //세션 데이터 출력
        session.getAttributeNames().asIterator()
                .forEachRemaining(name -> log.info("session name={}, value={}",
                        name, session.getAttribute(name)));
        log.info("sessionId={}", session.getId());
        log.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
        log.info("creationTime={}", new Date(session.getCreationTime()));
        log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
        log.info("isNew={}", session.isNew());
        return "세션 출력";
    }

    @GetMapping("/admin")
    public String adminPage(HttpServletRequest request) {
        return "admin";
    }
}
