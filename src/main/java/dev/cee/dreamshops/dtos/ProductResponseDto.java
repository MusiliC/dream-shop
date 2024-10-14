package dev.cee.dreamshops.dtos;


import java.math.BigDecimal;
import java.util.List;

import dev.cee.dreamshops.model.Category;

import lombok.Data;


@Data
public class ProductResponseDto {
    private Long id;

    private String name;

    private String brand;

    private BigDecimal price;

    private int inventory;

    private String description;

    private Category category;

    private List<ImageDto> images;
}
