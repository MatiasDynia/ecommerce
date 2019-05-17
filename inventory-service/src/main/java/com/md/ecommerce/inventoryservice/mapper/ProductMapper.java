package com.md.ecommerce.inventoryservice.mapper;

import com.md.ecommerce.inventoryservice.domain.Product;
import com.md.ecommerce.inventoryservice.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    static ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product productEntityToProduct(ProductEntity productEntity);
    ProductEntity productToProductEntity(Product product);
    List<Product> productEntityListToProductEntityList(List<ProductEntity> productEntities);
}
