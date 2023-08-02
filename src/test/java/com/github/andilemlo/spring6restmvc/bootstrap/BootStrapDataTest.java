package com.github.andilemlo.spring6restmvc.bootstrap;

import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import com.github.andilemlo.spring6restmvc.repositories.CustomerRepository;
import com.github.andilemlo.spring6restmvc.services.BeerCsvService;
import com.github.andilemlo.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootStrapDataTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootStrapData bookStrapData;

    @BeforeEach
    void setUp() {
        bookStrapData = new BootStrapData(beerRepository, customerRepository, beerCsvService);
    }

    @Test
    void run()throws Exception {
        bookStrapData.run(null);
        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);

    }
}