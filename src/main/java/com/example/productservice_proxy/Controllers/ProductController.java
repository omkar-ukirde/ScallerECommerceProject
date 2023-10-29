package com.example.productservice_proxy.Controllers;

import com.example.productservice_proxy.Services.IProductServices;
import com.example.productservice_proxy.clients.IClientProductDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController{
    private IProductServices productServices;
    public ProductController(IProductServices productServices){
        this.productServices = productServices;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(this.productServices.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("productId") Long productId){
        try {
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add("Accept", "application/json");
            header.add("Content-Type", "application/json");
            header.add("auth-tojen","heyaccess");
            if(productId < 1){
                throw new IllegalArgumentException("Something went wrong");
            }
            Product product =  productServices.getSingleProduct(productId);
            ResponseEntity<Product> responseEntity = new ResponseEntity<>(product, header,HttpStatus.OK);
            return responseEntity;
        }catch (Exception e){
            ResponseEntity<Product> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto productDto){
        Product product = getProduct(productDto);
        Product savedproduct = this.productServices.addNewProduct(product);
        ResponseEntity<Product> response = new ResponseEntity<>(savedproduct, HttpStatus.OK);
        return response;
    }
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategory(new Categories());
        product.getCategory().setName(productDto.getCategory());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        return this.productServices.updateProduct(productId,product);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId){
        return "Deleting product " + productId;
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
}
