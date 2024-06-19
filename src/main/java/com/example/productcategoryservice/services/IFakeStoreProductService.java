package com.example.productcategoryservice.services;

import com.example.productcategoryservice.models.Product;
import java.util.*;

public interface IFakeStoreProductService {
    public Product getProductById(Long id);
    public Product createProduct(Product product);
    public List<Product> getAllProducts();
    public Product replaceProduct(Long id,Product product);
}
