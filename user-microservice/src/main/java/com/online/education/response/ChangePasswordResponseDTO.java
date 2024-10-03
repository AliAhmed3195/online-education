package com.online.education.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordResponseDTO {
    private String username;
    private String newPassword;
}
