package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.mappers.BeerMapper;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
public class BeerServiceJPA implements BeerService {

    BeerRepository beerRepository;
    BeerMapper beerMapper;

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public List<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Override
    public void patchBeerbyId(UUID beerId, BeerDTO beer) {

    }
}
