package com.nvp.domaci3.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
