package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
    void updateBeerByID(UUID beerId, Beer beer);

    List<Beer> listBeers();

    Beer getBeerByID (UUID id);

    Beer saveNewBeer(Beer beer);

    void deleteBeerByID(UUID beerID);

    void patchBeerbyId(UUID beerID, Beer beer);
}
