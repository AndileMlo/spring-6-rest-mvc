package com.github.andilemlo.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrderLine {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)",updatable = false,nullable = false)
    private UUID id;

    private String beer_id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created_date;

    @UpdateTimestamp
    private Timestamp last_modified_date;

    private Integer order_quantity = 0;

    private Integer quantity_allocated = 0;

    public boolean isNew()
    { return this.id == null;}

    @Version
    private Long version;

    private String beer_order_id;

}
