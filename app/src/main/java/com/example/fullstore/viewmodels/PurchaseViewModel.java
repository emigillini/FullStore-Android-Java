package com.example.fullstore.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.CartProduct;
import com.example.fullstore.models.Delivery;
import com.example.fullstore.models.EmailRequest;
import com.example.fullstore.models.PaymentType;
import com.example.fullstore.models.Purchase;
import com.example.fullstore.models.PurchaseConfirmResponse;
import com.example.fullstore.models.SimplePurchase;
import com.example.fullstore.repository.EmailRepository;
import com.example.fullstore.repository.PaymentTypeRepository;
import com.example.fullstore.repository.PurchaseRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseViewModel extends ViewModel {
    private PurchaseRepository purchaseRepository;
    private PaymentTypeRepository paymentMethodRepository;
    private SessionManager sessionManager;
    private EmailRepository emailRepository;
    private MutableLiveData<List<PaymentType>> paymentMethodsLiveData = new MutableLiveData<>();
    private MutableLiveData<PurchaseConfirmResponse> purchaseLiveData = new MutableLiveData<>();
    private MutableLiveData<String> purchaseErrorLiveData = new MutableLiveData<>();
    private MutableLiveData<List<SimplePurchase>> purchaseListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> sessionExpiredLiveData = new MutableLiveData<>(false);

    public PurchaseViewModel() {

    }

    public void setSessionManager(SessionManager sessionManager, Context context) {
        this.sessionManager = sessionManager;
        this.purchaseRepository = new PurchaseRepository(sessionManager, context);
        this.paymentMethodRepository = new PaymentTypeRepository(sessionManager, context);
        this.emailRepository = new EmailRepository(sessionManager, context);
    }

    public LiveData<List<PaymentType>> getPaymentMethods() {
        return paymentMethodsLiveData;
    }

    public LiveData<PurchaseConfirmResponse> getPurchaseLiveData() {
        return purchaseLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return purchaseErrorLiveData;
    }

    public LiveData<List<SimplePurchase>> getPurchaseListLiveData() {
        return purchaseListLiveData;
    }

    public LiveData<Boolean> getSessionExpiredLiveData() {
        return sessionExpiredLiveData;
    }


    public void fetchPaymentMethods() {
        paymentMethodRepository.getPaymentMethods().enqueue(new Callback<List<PaymentType>>() {
            @Override
            public void onResponse(Call<List<PaymentType>> call, Response<List<PaymentType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    paymentMethodsLiveData.setValue(response.body());
                } else {
                    purchaseErrorLiveData.setValue("Failed to fetch payment methods.");
                }
            }

            @Override
            public void onFailure(Call<List<PaymentType>> call, Throwable t) {
                purchaseErrorLiveData.setValue("Error: " + t.getMessage());
            }
        });
    }


    public void purchaseConfirmResponse(String paymentType) {
        if (isSessionExpired()) {
            return;
        }
        purchaseRepository.purchaseConfirmRequest(paymentType).enqueue(new Callback<PurchaseConfirmResponse>() {
            @Override
            public void onResponse(Call<PurchaseConfirmResponse> call, Response<PurchaseConfirmResponse> response) {
                if (response.isSuccessful()) {
                    Log.i("MENSAJE", "Response body: " + response.body().toString());
                    purchaseLiveData.setValue(response.body());
                    sessionManager.setLastPurchase(response.body());
                    sendConfirmationEmail(response.body());
                } else {
                    Log.i("MENSAJE", "Response body: " + response.body().toString());
                    purchaseErrorLiveData.setValue("Purchase failed. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<PurchaseConfirmResponse> call, Throwable t) {
                Log.i("MENSAJE", "Response body: " + t);
                purchaseErrorLiveData.setValue(t.getMessage());
            }
        });
    }

    public void fetchUserPurchases() {
        if (isSessionExpired()) {
            return;
        }
        purchaseRepository.getUserPurchases().enqueue(new Callback<List<SimplePurchase>>() {
            @Override
            public void onResponse(Call<List<SimplePurchase>> call, Response<List<SimplePurchase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    purchaseListLiveData.setValue(response.body());
                } else {
                    purchaseErrorLiveData.setValue("Failed to fetch user purchases.");
                }
            }

            @Override
            public void onFailure(Call<List<SimplePurchase>> call, Throwable t) {
                purchaseErrorLiveData.setValue(t.getMessage());
            }
        });
    }

    private boolean isSessionExpired() {
        if (sessionManager.isTokenExpired()) {
            sessionExpiredLiveData.setValue(true);
            return true;
        }
        return false;
    }

    private void sendConfirmationEmail(PurchaseConfirmResponse purchaseResponse) {

        String subject = "Purchase Confirmation";
        String message = buildEmailContent(purchaseResponse); // Asegúrate de tener un método toString() adecuado en PurchaseConfirmResponse
        String toEmail = sessionManager.getUsername(); // Obtener el email del usuario desde el SessionManager

        EmailRequest emailRequest = new EmailRequest(subject, message, toEmail);

        emailRepository.sendEmail(emailRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("EMAIL", "Confirmation email sent successfully.");
                } else {
                    Log.e("EMAIL", "Failed to send confirmation email: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("EMAIL", "Error sending confirmation email: " + t.getMessage());
            }
        });

    }

    public String buildEmailContent(PurchaseConfirmResponse response) {
        Purchase purchase = response.getPurchase();
        StringBuilder emailContent = new StringBuilder();

        emailContent.append("<p>Thank you for your purchase!</p>")
                .append("<p>Invoice Number: ").append(purchase.getInvoice_number()).append("</p>")
                .append("<p>Total Amount: ").append(purchase.getTotal()).append(" USD</p>")
                .append("<p>Payment Type: ").append(purchase.getPaymentType()).append("</p>")
                .append("<p>Date: ").append(purchase.getDate()).append("</p>")
                .append("<p>Products:</p>");

        Cart cart = purchase.getCart();
        if (cart != null && cart.getProducts() != null) {
            for (CartProduct product : cart.getProducts()) {
                emailContent.append("<p>- ").append(product.getProduct().getModel())
                        .append(" (Quantity: ").append(product.getQuantity()).append(")</p>");
            }
        } else {
            emailContent.append("<p>No products found in your purchase.</p>");
        }

        emailContent.append("<p>Delivery Information:</p>");
        Delivery delivery = response.getDelivery();
        if (delivery != null) {
            emailContent.append("<p>Address: ").append(delivery.getDelivery_address()).append("</p>");
        }

        return emailContent.toString();
    }

}
