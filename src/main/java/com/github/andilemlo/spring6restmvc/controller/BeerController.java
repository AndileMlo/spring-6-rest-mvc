package com.github.andilemlo.spring6restmvc.controller;


import com.github.andilemlo.spring6restmvc.model.Beer;
import com.github.andilemlo.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j

@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET) //RequestMethod.GET)
    public List<Beer> listBeer(){
        return beerService.listBeers();
    }

    @PutMapping//@PutMapping("{beerId}")
    public ResponseEntity updatebyID(@PathVariable("beerId") UUID beerId , @RequestBody Beer beer){

        beerService.updateBeerByID(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteById (@PathVariable("beerId")UUID beerID){

        beerService.deleteBeerByID(beerID);

        return  new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PatchMapping("{beerId}")
    public ResponseEntity updateBeerbyId (@PathVariable("beerId")UUID beerID, @RequestBody Beer beer){

        beerService.patchBeerbyId(beerID, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @RequestMapping(value = "{beerID}", method = RequestMethod.GET)
    public Beer getBeerbyId(@PathVariable UUID beerID){

        log.debug("GetBeer ID- Controller was called 789");

        return beerService.getBeerByID(beerID);
    }

}
