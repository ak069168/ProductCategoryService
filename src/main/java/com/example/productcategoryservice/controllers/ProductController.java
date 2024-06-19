package com.example.productcategoryservice.controllers;

import com.example.productcategoryservice.dtos.CategoryDto;
import com.example.productcategoryservice.dtos.FakeStoreProductDto;
import com.example.productcategoryservice.dtos.ProductDto;
import com.example.productcategoryservice.models.Category;
import com.example.productcategoryservice.models.Product;
import com.example.productcategoryservice.services.FakeStoreProductService;
import com.example.productcategoryservice.services.IFakeStoreProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired

    IFakeStoreProductService fakeStoreProductService;
    @GetMapping
    public List<ProductDto> getAllProducts()
    {
        List<ProductDto> productDtos=new ArrayList<>();
        List<Product> products= fakeStoreProductService.getAllProducts();
        for(Product product:products)
        {
            ProductDto productDto=getProductDto(product);
            productDtos.add(productDto);
        }
        return productDtos;


    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long productId)
    {
        try {
            if (productId <= 0) {
                throw new IllegalArgumentException("Invalid Product Id");
            }
            Product product = fakeStoreProductService.getProductById(productId);
            ProductDto body = getProductDto(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Called by", "Abhishek");
            return new ResponseEntity<>(body, headers, HttpStatus.OK);
        }
        catch(IllegalArgumentException ex)
        {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping
    public ProductDto createProduct(ProductDto productDto)
    {
        return null;
    }

    @PutMapping("/{id}")
    public ProductDto replaceProduct(@PathVariable Long id,@RequestBody ProductDto productDto)
    {
        Product product=getProduct(productDto);
        Product newProduct=fakeStoreProductService.replaceProduct(id,product);
        return getProductDto(newProduct);
    }

    private Product getProduct(ProductDto productDto)
    {
        Product product=new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        Category category=new Category();
        category.setName(productDto.getCategory().getName());
        product.setCategory(category);
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        return product;

    }
    private ProductDto getProductDto(Product product)
    {
        ProductDto productDto=new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setPrice(product.getPrice());
        Category categoryDto=new Category();
        categoryDto.setName(product.getCategory().getName());
        productDto.setCategory(categoryDto);
        return productDto;
    }
}
