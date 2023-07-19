package com.github.andilemlo.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andilemlo.spring6restmvc.model.Customer;
import com.github.andilemlo.spring6restmvc.services.CustomerService;
import com.github.andilemlo.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

//import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CustomerController.class)

class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Autowired
    CustomerController customerController;

    @Test
    void testListCustomers () throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());


        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));

    }
/*
    @Test
    void testCreateNewCustomer(){
        ObjectMapper objectMapper = new ObjectMapper();



        System.out.println();



    }*/

    @Test
    void getCustomerById() throws Exception {

        Customer testCustomer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.getCustomerByID(testCustomer.getId())).willReturn(testCustomer);

         mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId())
                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                 .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));


        /*mockMvc.perform(get("/api/v1/customer" + testCustomer.getId())
                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((testCustomer.getId().toString()))))
                .andExpect(jsonPath("$.customerName" , is(testCustomer.getCustomerName())));*/

        //istCustomers()

               /* mockMvc.perform(get("/api/v1/beer/" +  testBeer.getId())
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                        .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));*/


    }
}