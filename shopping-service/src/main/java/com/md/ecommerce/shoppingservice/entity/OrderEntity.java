package com.md.ecommerce.shoppingservice.entity;

import com.md.ecommerce.commons.dto.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderEntity {

    @Id
    private ObjectId _id;
    private List<OrderProductEntity> products;
    private Client client;
    private OrderStateEntity orderState;
    private LocalDate date;
}
