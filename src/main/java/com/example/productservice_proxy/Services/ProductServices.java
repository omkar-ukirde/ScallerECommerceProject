package com.example.productservice_proxy.Services;

import com.example.productservice_proxy.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

public class ProductServices implements IProductServices {

    @Override
    public String getAllProducts(){
        return "Returning All Products";
    }
    @Override
    public String getSingleProduct(Long productId){
        return "Single Product " + productId;
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
