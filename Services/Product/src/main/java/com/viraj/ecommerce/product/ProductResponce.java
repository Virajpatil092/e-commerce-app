package com.viraj.ecommerce.product;


import java.math.BigDecimal;

public record ProductResponce(
        Integer id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}
