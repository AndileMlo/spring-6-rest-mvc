package com.github.andilemlo.spring6restmvc.bootstrap;

import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.entities.Customer;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.model.BeerStyle;
import com.github.andilemlo.spring6restmvc.model.CustomerDTO;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import com.github.andilemlo.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

   /* public BootStrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }*/

    @Override
    public void run(String... args) throws Exception{
        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Steve Rodgers")
                    .lastModifiedDate(LocalDateTime.now())
                    .createdDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Stan Smith")
                    .lastModifiedDate(LocalDateTime.now())
                    .createdDate(LocalDateTime.now())
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Peter Smith")
                    .lastModifiedDate(LocalDateTime.now())
                    .createdDate(LocalDateTime.now())
                    .build();
            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }
    }

    private void loadBeerData (){
        if (beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Alaskan beer")
                    .beerStyle(BeerStyle.WHEAT)
                    .upc("12345")
                    .price(new BigDecimal("12.98"))
                    .quantityOnHand(890)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            Beer beer2= Beer.builder()
                    .beerName("Bear nut milk")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("6789")
                    .price(new BigDecimal("17.98"))
                    .quantityOnHand(7890)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            Beer beer3= Beer.builder()
                    .beerName("Kenyean nectar")
                    .beerStyle(BeerStyle.ALE)
                    .upc("15678")
                    .price(new BigDecimal("8.98"))
                    .quantityOnHand(34890)
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }

        }
    }
