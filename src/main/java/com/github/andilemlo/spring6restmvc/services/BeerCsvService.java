package com.github.andilemlo.spring6restmvc.services;

import com.github.andilemlo.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
        List<BeerCSVRecord> convertCSV(File csvFile);
}
