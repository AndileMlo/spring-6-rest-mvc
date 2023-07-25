package com.github.andilemlo.spring6restmvc.mappers;

import com.github.andilemlo.spring6restmvc.entities.Customer;
import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO CustomertoCustomerDTO (Customer customer);
    Customer CustomerDtoToCustomer (CustomerDTO dto);
}
