package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Beer;
import com.github.andilemlo.spring6restmvc.model.BeerStyle;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    private Map<UUID,Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();


        Beer beer1= Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Alaskan beer")
                .beerStyle(BeerStyle.WHEAT)
                .upc("12345")
                .price(new BigDecimal("12.98"))
                .quantityOnHand(890)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer2= Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Bear nut milk")
                .beerStyle(BeerStyle.PILSNER)
                .upc("6789")
                .price(new BigDecimal("17.98"))
                .quantityOnHand(7890)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        Beer beer3= Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Kenyean nectar")
                .beerStyle(BeerStyle.ALE)
                .upc("15678")
                .price(new BigDecimal("8.98"))
                .quantityOnHand(34890)
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(),beer1);
        beerMap.put(beer2.getId(),beer2);
        beerMap.put(beer3.getId(),beer3);


    }


    @Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setPrice(beer.getPrice());
        existing.setUpc(beer.getUpc());
        existing.setQuantityOnHand(beer.getQuantityOnHand());

        beerMap.put(existing.getId(), existing);
    }

    @Override
    public List<Beer> listBeers(){

        return new ArrayList<>(beerMap.values());
    }


    @Builder
    @Override
    public Beer getBeerById(UUID id) {

        log.debug("(GetBeer ID- Service was called"
        );

        return beerMap.get(id);


    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void deleteBeerById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerbyId(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);//
        if (StringUtils.hasText(beer.getBeerName())){
            existing.setBeerName(beer.getBeerName());
        }
        if (beer.getBeerStyle() != null ) {
            existing.setBeerName(beer.getBeerName());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }


        if (beer.getPrice() != null ){
            existing.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null){
            existing.setQuantityOnHand(beer.getQuantityOnHand());

        }

    }
}
