package com.example.productservice_proxy.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    @GetMapping
    public String getAllCaegories(){
        return "Getting All Categories";
    }

    @GetMapping("/{categoryID}")
    public String getProductsInCategory(@PathVariable("categoryId") Long categoryID){
        return "Get products in category";
    }
}
