package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

    List<BeerDTO> listBeers(String beerName);

    Optional<BeerDTO> getBeerById (UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDTO> patchBeerbyId(UUID beerId, BeerDTO beer);
}
