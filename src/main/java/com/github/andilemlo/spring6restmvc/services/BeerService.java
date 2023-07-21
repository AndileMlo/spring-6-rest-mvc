package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    void updateBeerById(UUID beerId, BeerDTO beer);

    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById (UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    void deleteBeerById(UUID beerId);

    void patchBeerbyId(UUID beerId, BeerDTO beer);
}
