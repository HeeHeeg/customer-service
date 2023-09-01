package com.example.customer.controller;

import com.example.customer.config.JwtService;
import com.example.customer.config.TokenInfo;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.request.CustomerRequest;
import com.example.customer.domain.request.SignupRequest;
import com.example.customer.domain.response.SignupResponse;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final JwtService jwtService;

    @PostMapping("check")
    public SignupResponse check(
            @RequestHeader("Authorization") String token) { //토큰 가져오기.?
        TokenInfo tokenInfo = jwtService.parseToken(token.replace("Bearer ", ""));//Bearer를 지워서 이 토큰을 보내준다?
        return customerService.checkSignup(tokenInfo);
    }

    //이건 서버끼리 소통하는거라 우리가 딱히 손댈게 없다.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CustomerRequest request) {
        customerService.save(request);
    }

    @GetMapping("me")
    public Customer getMe(
            @RequestHeader("Authorization") String token) {
        TokenInfo tokenInfo = jwtService.parseToken(token.replace("Bearer ", ""));
        return customerService.getMe(tokenInfo);
    }

    @PostMapping("signup")
    public SignupResponse signup(@RequestBody SignupRequest request, @RequestHeader("Authorization") String token) {
        TokenInfo tokenInfo = jwtService.parseToken(token.replace("Bearer ", ""));
        return customerService.signup(request, tokenInfo);
    }

}
