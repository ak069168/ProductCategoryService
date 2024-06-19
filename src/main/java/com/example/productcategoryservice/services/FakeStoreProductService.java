package com.example.productcategoryservice.services;

import com.example.productcategoryservice.dtos.FakeStoreProductDto;
import com.example.productcategoryservice.models.Category;
import com.example.productcategoryservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;



@Service
public class FakeStoreProductService implements IFakeStoreProductService {
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Override
    public Product getProductById(Long productId)
    {
        RestTemplate restTemplate=restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto> responseEntity=restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,productId);
        if(responseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)))
        {
            return getProduct(responseEntity.getBody());
        }
        return null;
    }

    @Override
    public Product createProduct(Product product)
    {
        return null;
    }

    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate=restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    @Override
    public List<Product> getAllProducts()
    {
        RestTemplate restTemplate=restTemplateBuilder.build();
        List<Product> products=new ArrayList<>();

        FakeStoreProductDto[] fakeStoreProductDtos=restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos)
        {
            products.add(getProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product replaceProduct(Long id,Product product) {
        FakeStoreProductDto fakeStoreProductDto=getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductResponse=  requestForEntity(HttpMethod.PUT, "https://fakestoreapi.com/products/{id}", fakeStoreProductDto, FakeStoreProductDto.class, id).getBody();

        return getProduct(fakeStoreProductResponse);
    }

    private Product getProduct(FakeStoreProductDto fakeStoreProductDto)
    {
        Product product=new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        Category category=new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        return product;

    }

    private  FakeStoreProductDto getFakeStoreProductDto(Product product)
    {
        FakeStoreProductDto fakeStoreProductDto=new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setTitle(product.getName());
        if(product.getCategory()!=null)
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        return fakeStoreProductDto;
    }
}
