package com.example.customer.domain.request;

import com.example.customer.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@AllArgsConstructor
@Getter
public class CustomerRequest { // 이거는 우리가 생성 안함.
    //이것만 받아서 insert를 해줄거다.
    private UUID id;
    private String name;
    private String number;
    public Customer toEntity() {
        return Customer.builder()
                .id(id)
                .name(name)
                .number(number)
                .build();
    }
}
