package com.example.fullstore.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.AddProductResponse;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.CartProduct;
import com.example.fullstore.models.RemoveProductResponse;
import com.example.fullstore.repository.CartRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<Cart> cartLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>(false);
    private CartRepository cartRepository;
    private SessionManager sessionManager;

    public CartViewModel() {
    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.cartRepository = new CartRepository(sessionManager, context);
    }

    public LiveData<Cart> getCartLiveData() {
        return cartLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }

    public void fetchCart() {

        cartRepository.getCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, @NonNull Response<Cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("MENSAJE", "onResponse: " + response.body());
                    cartLiveData.setValue(response.body());
                } else {
                    errorLiveData.setValue("Error al obtener el carrito");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, @NonNull Throwable t) {
                errorLiveData.setValue("Error de red" + t);
            }
        });
    }

    public void addProductToCart(String productId, int quantity) {

        if (isSessionExpired()) {
            return;
        }

        cartRepository.addProductToCart(productId, quantity).enqueue(new Callback<AddProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddProductResponse> call, @NonNull Response<AddProductResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    cartLiveData.setValue(response.body().getCart());
                } else {
                    errorLiveData.setValue("Error adding product");
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddProductResponse> call, @NonNull Throwable t) {
                errorLiveData.setValue("Network error");
            }
        });
    }

    public void removeProductFromCart(String productId, int quantity) {

        if (isSessionExpired()) {
            return;
        }

        cartRepository.removeProductFromCart(productId, quantity).enqueue(new Callback<RemoveProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<RemoveProductResponse> call, @NonNull Response<RemoveProductResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    cartLiveData.setValue(response.body().getCart()); // Fetch updated cart
                } else {
                    errorLiveData.setValue("Error removing product");
                }
            }

            @Override
            public void onFailure(@NonNull Call<RemoveProductResponse> call, @NonNull Throwable t) {
                errorLiveData.setValue("Network error");
            }
        });
    }

    public double getCartTotal() {
        Cart currentCart = cartLiveData.getValue();
        if (currentCart != null && currentCart.getProducts() != null) {
            double total = 0;
            for (CartProduct cartProduct : currentCart.getProducts()) {
                total += cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
            }
            return total;
        }
        return 0;
    }

    public void createCart() {

        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Session expired");
            return;
        }

        cartRepository.createCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(@NonNull Call<Cart> call, @NonNull Response<Cart> response) {
                if (response.isSuccessful()) {
                    cartLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Error creating cart");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Cart> call, @NonNull Throwable t) {
                errorLiveData.postValue("Error in cart communication");
            }
        });
    }

    private boolean isSessionExpired() {
        if (sessionManager.isTokenExpired()) {
            errorLiveData.setValue("Session expired");
            sessionExpiredLiveData.setValue(true);
            return true;
        }
        return false;
    }

}

