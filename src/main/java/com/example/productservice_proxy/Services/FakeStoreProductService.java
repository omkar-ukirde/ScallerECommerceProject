package com.example.productservice_proxy.Services;

import com.example.productservice_proxy.clients.IClientProductDto;
import com.example.productservice_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductServices {
    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreClient fakeStoreClient;

    public FakeStoreProductService(RestTemplateBuilder restTemplate, FakeStoreClient fakeStoreClient){
        this.restTemplateBuilder = restTemplate;
        this.fakeStoreClient = fakeStoreClient;
    }
    private <T> ResponseEntity<T> requestForEntiry(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();

        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);

    }

    @Override
    public List<Product> getAllProducts(){
        /*
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto[]> responseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/",
                        ProductDto[].class);
         */
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreClient.getAllProducts();
        List<Product> products = new ArrayList<>();

        for(FakeStoreProductDto productDtos : fakeStoreProductDtos){
                Product product = new Product();
                product.setId(productDtos.getId());
                product.setTitle(productDtos.getTitle());
                product.setPrice(productDtos.getPrice());
                Categories category = new Categories();
                category.setName(productDtos.getCategory());
                product.setCategory(category);
                product.setImageURL(productDtos.getImage());
                product.setDescription(productDtos.getDescription());
                products.add(product);
        }
        return products;
    }
    @Override
    public Product getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> productDto =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}"
                                                ,FakeStoreProductDto.class,productId);

        Product product = getProduct(productDto.getBody());
        return product;
    }

    private Product getProduct(FakeStoreProductDto productDto) {
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

/*
    @Override
    public Product addNewProduct(IClientProductDto productDto){
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.postForEntity("https://fakestoreapi.com/products", productDto,ProductDto.class);
        Product product = getProduct((FakeStoreProductDto) productDto);
        return product;
    }
 */
    @Override
    public Product addNewProduct(Product product){
        return null;
    }

    @Override
    public Product updateProduct(Long productId, Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageURL());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntiry(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );

        FakeStoreProductDto fakeStoreProductDto1 = fakeStoreProductDtoResponseEntity.getBody();
        return getProduct(fakeStoreProductDto1);
    }

    @Override
    public String deleteProduct(Long productId){
        return "Deleting product " + productId;
    }
}
