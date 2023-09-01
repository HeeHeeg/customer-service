package com.example.customer.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Table(name = "customers")
@Entity @Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Customer {
    @Id //우리가 생성하지 않고 auth에서 생성할거니까 제너레이트벨류는 쓰지 않을거다.
    private UUID id;
    private String name;
    private String number;
    private String address; //고객 주소를 알아야 배달을 해주던말던 하니까. 고객주소.

    public void setAddress(String address) {
        this.address = address;
    }

}
