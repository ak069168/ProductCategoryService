package com.example.productcategoryservice.models;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter

public class Category extends BaseModel{
    private String name;
    private String description;

    private List<Product> productList=new ArrayList<>();
}
