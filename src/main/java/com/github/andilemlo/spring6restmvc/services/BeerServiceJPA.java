package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.mappers.BeerMapper;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beer) {

    }

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::BeerToBeerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper
                .BeerToBeerDTO(beerRepository
                        .findById(id)
                        .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        return beerMapper.BeerToBeerDTO(beerRepository.save(beerMapper.BeerDtoToBeer(beer)));
    }

    @Override
    public void deleteBeerById(UUID beerId) {

    }

    @Override
    public void patchBeerbyId(UUID beerId, BeerDTO beer) {

    }
}
