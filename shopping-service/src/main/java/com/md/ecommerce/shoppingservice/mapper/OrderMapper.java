package com.md.ecommerce.shoppingservice.mapper;

import com.md.ecommerce.commons.dto.Order;
import com.md.ecommerce.shoppingservice.entity.OrderEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Named("mapTotal")
    @Mapping(target = "total", source = "order")
    Order map(OrderEntity order);

    @IterableMapping(qualifiedByName = "mapTotal")
    Iterable<Order> map(Iterable<OrderEntity> orders);

    default Double orderToTotal(OrderEntity order) {

        return order.getProducts().stream()
                .mapToDouble(o -> o.getProduct().getPrice().getAmount() * o.getQuantity()).sum();
    }
}
