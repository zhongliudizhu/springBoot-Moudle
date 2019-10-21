package com.winstar.repository;

import com.winstar.entity.NewCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewCustomerRepository extends JpaRepository<NewCustomer, String> {
}
