package com.example.order.service.adapters.customer.mapper;

import com.example.order.service.adapters.customer.jpa.entity.CustomerEntity;
import com.example.order.service.customer.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "id", target = "id")
    Customer map(CustomerEntity customerEntity);


}
