package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    void updateBeerById(UUID beerId, Beer beer);

    List<Beer> listBeers();

    Beer getBeerById (UUID id);

    Beer saveNewBeer(Beer beer);

    void deleteBeerById(UUID beerId);

    void patchBeerbyId(UUID beerId, Beer beer);
}
