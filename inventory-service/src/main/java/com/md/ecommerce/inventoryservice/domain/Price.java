package com.md.ecommerce.inventoryservice.domain;

import lombok.Data;

import javax.persistence.*;

@Data
public class Price {

    private Long id;
    private Double amount;
}
