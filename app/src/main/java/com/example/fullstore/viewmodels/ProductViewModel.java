package com.example.fullstore.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.Product;
import com.example.fullstore.repository.ProductRepository;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductViewModel extends ViewModel {
    private ProductRepository productRepository;
    private SessionManager sessionManager;
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();
    private MutableLiveData<Product> productLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public ProductViewModel() {

    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.productRepository = new ProductRepository(sessionManager, context);
    }

    public LiveData<List<Product>> getProductListLiveData() {
        return productListLiveData;
    }

    public LiveData<Product> getProductLiveData() {
        return productLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchAllProducts() {
        productRepository.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    List<Product> productList = response.body();

                    if (productList != null) {

                        productListLiveData.postValue(productList);
                    }
                } else {
                    errorLiveData.postValue("Error fetching products");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void fetchProductById(String id) {

        productRepository.getProductById(id).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful()) {
                    productLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error fetching product");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

    public void fetchProductsByBrand(String brandName) {
        productRepository.getProductsByBrand(brandName).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productListLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error fetching products by brand");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }

}
