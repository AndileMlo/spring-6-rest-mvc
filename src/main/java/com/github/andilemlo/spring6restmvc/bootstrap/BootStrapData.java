package com.github.andilemlo.spring6restmvc.bootstrap;

import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.entities.Customer;
import com.github.andilemlo.spring6restmvc.model.BeerCSVRecord;
import com.github.andilemlo.spring6restmvc.model.BeerStyle;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import com.github.andilemlo.spring6restmvc.repositories.CustomerRepository;
import com.github.andilemlo.spring6restmvc.services.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

   /* public BootStrapData(BeerRepository beerRepository, CustomerRepository customerRepository) {
        this.beerRepository = beerRepository;
        this.customerRepository = customerRepository;
    }*/

    @Transactional
    @Override
    public void run(String... args) throws Exception{
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if(beerRepository.count()<10){
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(),50))
                                .beerStyle(beerStyle)
                                .price(BigDecimal.TEN)
                                .upc(beerCSVRecord.getRow().toString())
                                .quantityOnHand(beerCSVRecord.getCount())
                                .build());
        });}
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
