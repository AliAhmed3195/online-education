package com.online.education.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token_expiry")
    private int refreshTokenExpiry;

//    @JsonProperty("is_otp_required")
//    private boolean isOtpRequired;
//
//    @JsonProperty("force_password_change")
//    private boolean isForcePasswordChange;

    @JsonProperty("user_type_id")
    private String userTypeId;
}
