package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> ListCustomers();

    Customer getCustomerByID (UUID id);

    Customer saveCustomer(Customer customer);

    void updateByID(UUID customerID, Customer customer);

    void deleteCustomerbyId(UUID uuid, Customer customer);
}
