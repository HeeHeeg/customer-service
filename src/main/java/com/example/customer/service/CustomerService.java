package com.example.customer.service;

import com.example.customer.config.TokenInfo;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.request.CustomerRequest;
import com.example.customer.domain.request.SignupRequest;
import com.example.customer.domain.response.SignupResponse;
import com.example.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer save(CustomerRequest request) { //auth서버에서 갖다주는 데이터
        return customerRepository.save(request.toEntity()); //처음에는 address가 비어있으면됨.
    }

    //검증> 토큰 까기?
    public SignupResponse checkSignup(TokenInfo tokenInfo) { //저쪽에서 토큰을 주니까 이걸 까서 사용할거임.
        Customer customer = getByToken(tokenInfo);
        return SignupResponse.builder()
                .customer(customer)
                .redirect(customer.getAddress() == null? "/signup":"/main") // 주소가 null이면 signup으로 보내고 아니면 메인으로 보낸다.
                .build();
    }
    @Transactional //더디체킹?
    public SignupResponse signup(SignupRequest request, TokenInfo tokenInfo) {
        Customer customer = getByToken(tokenInfo);
        customer.setAddress(request.getAddress());  //업데이트 되도록. address만 따로 set을 만들어줌. 전체가 set되지 않도록.
        return SignupResponse.builder()
                .customer(customer)
                .redirect("/main") // 주소가 null이면 signup으로 보내고 아니면 메인으로 보낸다.
                .build();

    }

    public Customer getMe(TokenInfo tokenInfo) {
        return customerRepository.findById(tokenInfo.getId())
                .orElseThrow(() -> new IllegalArgumentException("USER NOT FOUND"));
    }



    //공용 로직은 아래에 두는게 편하다. 여기에서만 쓰는것은.
    private Customer getByToken(TokenInfo tokenInfo) {
        UUID id = tokenInfo.getId();
        Customer customer;
        Optional<Customer> byId = customerRepository.findById(id);
        //save시점이 지났지만 null일수도 있음. 그래서 byid가 없으면 저장해주도록 save.
        if (byId.isEmpty()) {
            CustomerRequest customerRequest = new CustomerRequest(id, tokenInfo.getName(), tokenInfo.getNumber());
            return save(customerRequest);
        } else {
            return byId.get();
        }
    }
}
