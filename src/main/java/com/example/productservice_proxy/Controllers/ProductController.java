package com.example.productservice_proxy.Controllers;

import com.example.productservice_proxy.Services.IProductServices;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("products")
public class ProductController{
    private IProductServices productServices;
    public ProductController(IProductServices productServices){
        this.productServices = productServices;
    }

    @GetMapping
    public String getAllProducts(){
        return "Returning All Products";
    }
    @GetMapping("/{productId}")
    public Product getSingleProduct(@PathVariable("productId") Long productId){
        Product product =  productServices.getSingleProduct(productId);
        return product;
    }

    @PostMapping("")
    public String addNewProduct(@RequestBody ProductDto productDto){
        return "Adding new product "+ productDto;
    }
    @PutMapping("/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId){
        return "Updating product " + productId;
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId){
        return "Deleting product " + productId;
    }
}
