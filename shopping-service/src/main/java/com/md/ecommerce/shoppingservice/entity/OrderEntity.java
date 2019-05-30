package com.md.ecommerce.shoppingservice.entity;

import com.md.ecommerce.commons.dto.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderEntity {

    @Transient
    public static final String SEQUENCE_NAME = "orders_sequence";

    @Id
    private String id;
    private List<OrderProductEntity> products;
    private Client client;
    private OrderStateEntity orderState;
    private LocalDate date;
}
