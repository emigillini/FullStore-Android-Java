package com.example.fullstore.repository;

import android.content.Context;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.api.ProductService;
import com.example.fullstore.api.RetrofitClient;
import com.example.fullstore.models.Product;

import java.util.List;

import retrofit2.Call;

public class ProductRepository {
    private ProductService productService;

    public ProductRepository(SessionManager sessionManager, Context context) {
        productService = RetrofitClient.getRetrofit(sessionManager, context).create(ProductService.class);
    }

    public Call<List<Product>> getAllProducts() {

        return productService.getAllProducts();
    }

    public Call<Product> getProductById(String id) {
        return productService.getProductById(id);
    }

    public Call<List<Product>> getProductsByBrand(String brandName) {
        return productService.getProductsByBrand(brandName);
    }
}