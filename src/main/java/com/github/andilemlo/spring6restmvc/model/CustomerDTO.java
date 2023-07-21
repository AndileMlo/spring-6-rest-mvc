package com.github.andilemlo.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id; // id version createdDate
    private Integer version;

    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
