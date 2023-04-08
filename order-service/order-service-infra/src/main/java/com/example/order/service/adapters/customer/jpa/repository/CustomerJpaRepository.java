package com.example.order.service.adapters.customer.jpa.repository;

import com.example.order.service.adapters.customer.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerJpaRepository extends JpaRepository<CustomerEntity, UUID> {
}
