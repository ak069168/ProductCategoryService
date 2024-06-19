package com.example.productcategoryservice.dtos;

import com.example.productcategoryservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter

public class CategoryDto {
    private String name;
    private String description;

//    private List<Product> productList=new ArrayList<>();
}
