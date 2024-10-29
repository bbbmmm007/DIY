package com.zhny.computer.DTO;

import lombok.Data;

@Data
public class AdminUpPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
