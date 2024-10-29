package com.zhny.computer.DTO;

import lombok.Data;

@Data
public class UserUpPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
