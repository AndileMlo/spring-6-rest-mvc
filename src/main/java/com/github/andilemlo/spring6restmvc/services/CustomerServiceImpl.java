package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Customer;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Steve Rodgers")
                .version(23)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .customerName("Stan Smith")
                .version(78)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);

    }

    @Override
    public List ListCustomers(){
        return new ArrayList(customerMap.values());
    }

    @Builder
    @Override
    public Customer getCustomerByID(UUID id) {
        return customerMap.get(id);
    }

    @Override
    public Customer saveCustomer(Customer customer) {

        Customer savedCustomer = Customer.builder()
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .customerName(customer.getCustomerName())
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(),savedCustomer);

        return savedCustomer;
    }

    @Override
    public void updateByID(UUID customerID, Customer customer) {
        Customer existing = customerMap.get(customerID);
        existing.setCustomerName(customer.getCustomerName());
        customerMap.put(existing.getId(),existing);
    }

    @Override
    public void deleteCustomerbyId(UUID customerID, Customer customer) {
        customerMap.remove(customerID);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())){
        existing.setCustomerName(customer.getCustomerName());}

    }

}
