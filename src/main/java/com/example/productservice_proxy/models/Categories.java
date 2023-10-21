package com.example.productservice_proxy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Categories extends BaseModel{
    private String name;
    private String description;
    private List<Product> productsList;
}
