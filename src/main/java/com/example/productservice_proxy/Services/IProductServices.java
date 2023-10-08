package com.example.productservice_proxy.Services;

import com.example.productservice_proxy.dtos.ProductDto;

public interface IProductServices {
    String getAllProducts();

    String getSingleProduct(Long productId);

    String addNewProduct(ProductDto productDto);

    String updateProduct(Long productId);

    String deleteProduct(Long productId);
}
