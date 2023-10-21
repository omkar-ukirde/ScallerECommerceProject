package com.example.productservice_proxy.Services;

import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ProductServices implements IProductServices {
    private RestTemplateBuilder restTemplateBuilder;
    public ProductServices(RestTemplateBuilder restTemplate){
        this.restTemplateBuilder = restTemplate;
    }


    @Override
    public String getAllProducts(){
        return "Returning All Products";
    }
    @Override
    public Product getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ProductDto productDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}",ProductDto.class,productId)
                                            .getBody();

        Product product = getProduct(productDto);
        return product;
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        Categories category = new Categories();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageURL(productDto.getImage());
        product.setDescription(productDto.getDescription());
        return product;
    }

    @Override
    public String addNewProduct(ProductDto productDto){
        return "Adding new product "+ productDto;
    }
    @Override
    public String updateProduct(Long productId){
        return "Updating product " + productId;
    }

    @Override
    public String deleteProduct(Long productId){
        return "Deleting product " + productId;
    }
}
