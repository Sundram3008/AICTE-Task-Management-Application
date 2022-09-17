package com.sundram.aictetaskmanagement.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FogotPassword {
    private String newPassword;
    private String confirmPassword;
}
