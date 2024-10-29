package com.zhny.computer.DTO;

import lombok.Data;

import javax.servlet.http.HttpSession;

@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
