package com.md.ecommerce.inventoryservice.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "prices")
public class PriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double amount;
}
