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

public class BeerController {

    public static final String BEER_PATH  = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;



    @PutMapping(BEER_PATH_ID)//@PutMapping("{beerId}")
    public ResponseEntity updatebyID(@PathVariable("beerId") UUID beerId , @RequestBody Beer beer){

        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById (@PathVariable("beerId")UUID beerID){

        beerService.deleteBeerById(beerID);

        return  new ResponseEntity(HttpStatus.NO_CONTENT);

    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerbyId (@PathVariable("beerId")UUID beerID, @RequestBody Beer beer){

        beerService.patchBeerbyId(beerID, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer){

        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);

    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();

    }


    @GetMapping(value = BEER_PATH_ID)
    public Beer getBeerbyId(@PathVariable UUID beerId){

        log.debug("GetBeer ID- Controller was called 789");

        return beerService.getBeerById(beerId);
    }

    @GetMapping(value = BEER_PATH) //RequestMethod.GET)
    public List<Beer> listBeer(){
        return beerService.listBeers();
    }

}
