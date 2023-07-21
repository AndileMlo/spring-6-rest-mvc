package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface CustomerService {
    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById (UUID id);

    Customer saveCustomer(Customer customer);

    void updateById(UUID customerID, Customer customer);

    void deleteCustomerbyId(UUID uuid);

    void patchCustomerById(UUID customerId, Customer customer);
}
