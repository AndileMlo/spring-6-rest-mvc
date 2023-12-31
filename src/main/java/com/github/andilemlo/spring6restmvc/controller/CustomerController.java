package com.github.andilemlo.spring6restmvc.controller;


import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import com.github.andilemlo.spring6restmvc.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController//@RestController


public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> consumerList(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteById (@PathVariable("customerId") UUID customerId){
        customerService.deleteCustomerbyId(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customer){

        customerService.patchCustomerById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

   @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerID, @RequestBody CustomerDTO customer){
        customerService.updateById(customerID, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_PATH)
    public ResponseEntity handlePost(@RequestBody CustomerDTO customer){


        CustomerDTO savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" ,  "/api/v1/customer/" +savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable UUID customerId){

        //  log.debug("GetBeer ID- Controller was called");

        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

}
