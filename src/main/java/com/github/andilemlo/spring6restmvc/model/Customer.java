package com.github.andilemlo.spring6restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private UUID id; // id version createdDate
    private Integer version;

    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
