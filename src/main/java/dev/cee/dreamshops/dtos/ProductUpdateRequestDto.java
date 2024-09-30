package dev.cee.dreamshops.dtos;

import java.math.BigDecimal;

import dev.cee.dreamshops.model.Category;
import lombok.Data;

@Data
public class ProductUpdateRequestDto {
    private String name;

    private String brand;

    private BigDecimal price;

    private int inventory;

    private String description;

    private Category category;
}
