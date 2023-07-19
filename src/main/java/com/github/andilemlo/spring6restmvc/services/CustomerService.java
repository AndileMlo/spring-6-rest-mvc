package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Customer;
import lombok.Data;

import java.util.List;
import java.util.UUID;



public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerByID (UUID id);

    Customer saveCustomer(Customer customer);

    void updateByID(UUID customerID, Customer customer);

    void deleteCustomerbyId(UUID uuid, Customer customer);

    void patchCustomerById(UUID customerId, Customer customer);
}
