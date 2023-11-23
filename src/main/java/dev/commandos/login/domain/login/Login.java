package dev.commandos.login.domain.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Login {

    @NotEmpty
    private String loginId; //로그인 ID

    @NotEmpty
    private String password;
}
