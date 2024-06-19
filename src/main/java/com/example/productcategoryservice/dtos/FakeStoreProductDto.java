package com.example.productcategoryservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private String title;
    private Long id;
    private String description;
    private String image;
    private String category;
    private Double price;
    private FakeStoreRatingDto rating;
}

