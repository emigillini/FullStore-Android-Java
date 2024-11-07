package com.example.fullstore.api;

import com.example.fullstore.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @GET("products/brand/{brandName}")
    Call<List<Product>> getProductsByBrand(@Path("brandName") String brandName);
}