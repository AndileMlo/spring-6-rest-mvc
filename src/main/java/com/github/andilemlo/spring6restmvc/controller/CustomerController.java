package com.github.andilemlo.spring6restmvc.controller;


import com.github.andilemlo.spring6restmvc.model.Customer;
import com.github.andilemlo.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController//@RestController

@RequestMapping("/api/v1/customer")//@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> consumerList(){
        return customerService.ListCustomers();
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity deleteById (@PathVariable("customerId") UUID customerId, Customer customer){
        customerService.deleteCustomerbyId(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

   @PutMapping("{customerId}")
    public ResponseEntity updateByID(@PathVariable("customerId") UUID customerID, @RequestBody Customer customer){
        customerService.updateByID(customerID, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer){


        Customer savedCustomer = customerService.saveCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location" ,  "/api/v1/customer/" +savedCustomer.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{customerID}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable UUID customerID){

        //  log.debug("GetBeer ID- Controller was called");

        return customerService.getCustomerByID(customerID);
    }

}
