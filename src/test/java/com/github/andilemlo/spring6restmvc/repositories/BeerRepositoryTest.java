package com.github.andilemlo.spring6restmvc.repositories;

import com.github.andilemlo.spring6restmvc.controller.NotFoundException;
import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong(){
        assertThrows(ConstraintViolationException.class, ()-> {

            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My New and improved beer 1234567890 1234567890 1234567890 1234567890 1234567890 1234567890")
                    .beerStyle(BeerStyle.ALE)
                    .upc("567897656")
                    .price(new BigDecimal("11.99"))
                    .build());

            beerRepository.flush();
        });

    }


    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My New and improved beer")
                        .beerStyle(BeerStyle.ALE)
                        .upc("567897656")
                        .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}