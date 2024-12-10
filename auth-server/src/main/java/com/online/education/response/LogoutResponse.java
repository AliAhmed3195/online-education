package com.online.education.response;

import lombok.Data;

@Data
public class LogoutResponse {
    private String loginTime;
    private String logoutTime;
    private String duration;
}
