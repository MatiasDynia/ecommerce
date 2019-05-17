package com.md.ecommerce.inventoryservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double amount;
}
