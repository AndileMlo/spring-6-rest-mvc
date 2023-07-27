package com.github.andilemlo.spring6restmvc.controller;

import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.entities.Customer;
import com.github.andilemlo.spring6restmvc.mappers.BeerMapper;
import com.github.andilemlo.spring6restmvc.mappers.CustomerMapper;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import com.github.andilemlo.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;



    void testDeleteByIdNotFound(){

        assertThrows(NotFoundException.class,() ->
                customerController.deleteById(UUID.randomUUID()));
    }


@Transactional
    @Rollback
    @Test
    void deleteFoundId() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteById(customer.getId());

        assertThat(customerRepository.findById(customer.getId()).isEmpty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    void testUpdateNotFound(){

        assertThrows(NotFoundException.class,() ->
                customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Transactional
    @Rollback
    @Test
    void testUpdateCustomerById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.CustomertoCustomerDTO(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String customerName = "UPDATED";
        customerDTO.setCustomerName(customerName);

        ResponseEntity responseEntity = customerController.updateById(customer.getId(),customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(customerName);

    }

    @Test
    void testCustomerNotFound() {
        assertThrows(NotFoundException.class,() ->
                customerController.getCustomerById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void testSaveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Customer")
                .build();

        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testGetCustomerByID() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO dto = customerController.getCustomerById(customer.getId());
        assertThat(dto).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void testEmptyListCustomers() {
        customerRepository.deleteAll();

        List<CustomerDTO> dtos = customerController.consumerList();
        assertThat(dtos.size()).isEqualTo(0);
    }


    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos = customerController.consumerList();
        assertThat(dtos.size()).isEqualTo(3);
    }
}
