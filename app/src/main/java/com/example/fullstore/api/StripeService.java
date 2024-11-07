package com.example.fullstore.api;


import android.content.Context;

import androidx.annotation.NonNull;

import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.ApiResultCallback;

public class StripeService {
    private final Stripe stripe;

    public StripeService(Context context, String publishableKey) {
        this.stripe = new Stripe(context, publishableKey);
    }

    public void createPaymentMethod(CardInputWidget cardInputWidget, PaymentMethodCreationCallback callback) {

        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

        if (params != null) {
            stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
                @Override
                public void onSuccess(@NonNull PaymentMethod paymentMethod) {
                    callback.onPaymentMethodCreated(paymentMethod, null);
                }

                @Override
                public void onError(@NonNull Exception e) {
                    callback.onPaymentMethodCreated(null, e);
                }
            });
        } else {
            callback.onPaymentMethodCreated(null, new Throwable("Invalid card data"));
        }
    }

    public interface PaymentMethodCreationCallback {
        void onPaymentMethodCreated(PaymentMethod paymentMethod, Throwable error);
    }
}
