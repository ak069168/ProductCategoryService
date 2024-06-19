package com.example.productcategoryservice.dtos;

import com.example.productcategoryservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private Category category;
}
