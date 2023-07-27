package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.mappers.CustomerMapper;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import com.github.andilemlo.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::CustomertoCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.ofNullable(customerMapper
                .CustomertoCustomerDTO(customerRepository
                        .findById(id)
                        .orElse(null)));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {

        return customerMapper.CustomertoCustomerDTO(customerRepository.save(customerMapper.CustomerDtoToCustomer(customer)));
    }

    @Override
    public Optional<CustomerDTO> updateById(UUID customerID, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerID).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setCustomerName(customer.getCustomerName());

            atomicReference.set(Optional.of(customerMapper
                    .CustomertoCustomerDTO(customerRepository.save(foundCustomer))));
        },() -> {atomicReference.set(Optional.empty());});

        return  atomicReference.get();
    }


    @Override
    public Boolean deleteCustomerbyId(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;

    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getCustomerName())){
                foundCustomer.setCustomerName(customer.getCustomerName());}
            atomicReference.set(Optional.of(customerMapper
                    .CustomertoCustomerDTO(customerRepository.save(foundCustomer))));
        },() -> {atomicReference.set(Optional.empty());});
        return atomicReference.get();
        }

}
