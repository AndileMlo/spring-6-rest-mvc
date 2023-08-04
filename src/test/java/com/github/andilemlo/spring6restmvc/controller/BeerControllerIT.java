package com.github.andilemlo.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.andilemlo.spring6restmvc.entities.Beer;
import com.github.andilemlo.spring6restmvc.mappers.BeerMapper;
import com.github.andilemlo.spring6restmvc.model.BeerDTO;
import com.github.andilemlo.spring6restmvc.model.BeerStyle;
import com.github.andilemlo.spring6restmvc.repositories.BeerRepository;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BeerMapper beerMapper;


    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;


     @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    void testListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName","IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "false"))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$.size()", is(310)))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.nullValue()));
        ;
    }

    @Test
    void testListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName","IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(310)))
                .andExpect(jsonPath("$.[0].quantityOnHand").value(IsNull.notNullValue()));
        ;
    }

    @Test
    void testListBeersByNameAndStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName","IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(310)));
    }

    @Test
    void testListBeersByStyle() throws Exception {
         mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerStyle", BeerStyle.IPA.name()))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.size()", is(547)));
    }

    @Test
    void testListBeersByName() throws Exception {
    mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName","IPA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()",is(336)));
    }

    @Test
    void testPatchBeersBadName() throws Exception{
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name1234567890 1234567890 123456789012345678901234567890123456789012345678901234567890");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1)))
        .andReturn();

    }


    @Rollback
    @Transactional
    @Test
    void patchExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.patchBeerbyId(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }




    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                beerController.deleteById(UUID.randomUUID()));}

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(beerRepository.findById(beer.getId()).isEmpty());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        //Beer foundBeer = beerRepository.findById(beer.getId()).get();
       // assertThat(foundBeer).isNull();
    }



    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () ->
                beerController.updatebyID(UUID.randomUUID(),BeerDTO.builder().build()));


    }
    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updatebyID(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }



    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }




    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, ()-> {
            beerController.getBeerbyId(UUID.randomUUID());});

    }

    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO dto = beerController.getBeerbyId(beer.getId());
        assertThat(dto).isNotNull();

    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers(null, null, false);
         assertThat(dtos.size()).isEqualTo(2413);
    }


    @Rollback
    @Transactional
    @Test
    void testEmptyBeers() {
        beerRepository.deleteAll();

        List<BeerDTO> dtos = beerController.listBeers(null, null, false);
        assertThat(dtos.size()).isEqualTo(0);
    }
}