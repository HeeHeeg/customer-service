package com.example.customer.domain.response;


import com.example.customer.config.TokenInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse {
    private TokenInfo tokenInfo;
    private String redirect;

}
