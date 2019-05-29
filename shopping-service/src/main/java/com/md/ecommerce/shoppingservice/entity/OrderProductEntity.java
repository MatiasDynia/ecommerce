package com.md.ecommerce.shoppingservice.entity;

import com.md.ecommerce.commons.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductEntity {

    private Product product;
    private int quantity;
}
