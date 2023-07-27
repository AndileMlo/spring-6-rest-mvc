package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import lombok.Builder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Steve Rodgers")
                .version(23)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Stan Smith")
                .version(78)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Peter Smith")
                .version(78)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<CustomerDTO> getAllCustomers(){
        return new ArrayList(customerMap.values());
    }

    @Builder
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {

        CustomerDTO savedCustomer = CustomerDTO.builder()
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
    public Optional<CustomerDTO> updateById(UUID customerID, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerID);
        existing.setCustomerName(customer.getCustomerName());
        customerMap.put(existing.getId(),existing);

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteCustomerbyId(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getCustomerName())){
        existing.setCustomerName(customer.getCustomerName());}


        return Optional.of(existing);
    }

}
