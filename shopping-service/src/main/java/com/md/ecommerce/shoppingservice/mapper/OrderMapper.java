package com.md.ecommerce.shoppingservice.mapper;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "total", source = "order")
    Order map(OrderEntity order);

    default Double orderToTotal(OrderEntity order) {

        return order.getProducts().stream()
                .mapToDouble(o -> o.getProduct().getPrice().getAmount() * o.getQuantity()).sum();
    }
}
