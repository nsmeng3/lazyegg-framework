package io.lazyegg.boot.usermanagement.domain.customer;

import com.alibaba.cola.domain.Entity;
import lombok.Data;

//Domain Entity can choose to extend the domain model which is used for DTO
@Data
@Entity
public class Customer {

    private String customerId;


}
