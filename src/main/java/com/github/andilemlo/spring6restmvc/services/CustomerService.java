package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById (UUID id);

    CustomerDTO saveCustomer(CustomerDTO customer);

    void updateById(UUID customerID, CustomerDTO customer);

    void deleteCustomerbyId(UUID uuid);

    void patchCustomerById(UUID customerId, CustomerDTO customer);
}
