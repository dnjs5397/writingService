package com.writingService.writingService.User;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "E-mail은 필수 항목입니다.")
    @Email
    private String email;

    @Size(min = 8, max = 30)
    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password1;

    private String password2;

    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자 ID는 필수 항목입니다.")
    private String username;

}