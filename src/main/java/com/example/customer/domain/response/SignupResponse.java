package com.example.customer.domain.response;


import com.example.customer.config.TokenInfo;
import com.example.customer.domain.entity.Customer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupResponse {
    private Customer customer; //왠만한 상황에서는 엔티티를 반환해주는게 좋지는 않지만 address를 반환 못해줘서 Customer를 반환하도록.
    private String redirect;

}
