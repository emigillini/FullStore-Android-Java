package com.example.fullstore.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fullstore.Adapter.CartAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.PaymentType;
import com.example.fullstore.models.PurchaseConfirmResponse;
import com.example.fullstore.api.StripeService;
import com.stripe.android.view.CardInputWidget;
import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends BaseFragment {
    private CartAdapter cartAdapter;
    private TextView tvTotalPurchase;
    private Button confirmPurchaseButton;
    private RadioGroup radioGroupPaymentMethods;
    private CardInputWidget cardInputWidget;
    private LinearLayout example;
    private StripeService stripeService;

    public PaymentFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        cartViewModel.fetchCart();
        RecyclerView rvPurchaseItems = view.findViewById(R.id.recyclerViewPurchaseItems);
        tvTotalPurchase = view.findViewById(R.id.totalPurchaseAmount);
        confirmPurchaseButton = view.findViewById(R.id.confirmPurchaseButton);
        radioGroupPaymentMethods = view.findViewById(R.id.radioGroupPaymentMethods);
        cardInputWidget = view.findViewById(R.id.card_input_widget);
        example = view.findViewById(R.id.exampleDataLayout);
        example.setVisibility(View.GONE);
        cardInputWidget.setVisibility(View.GONE);

        int orientation = getResources().getConfiguration().orientation;


        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvPurchaseItems.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        } else {
            rvPurchaseItems.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        }



        cartAdapter = new CartAdapter(new ArrayList<>(), getContext(), null, false);
        rvPurchaseItems.setAdapter(cartAdapter);

        stripeService = new StripeService(getContext(), getString(R.string.pk_test_TU_CLAVE_PUBLICA));

        purchaseViewModel.getPaymentMethods().observe(getViewLifecycleOwner(), new Observer<List<PaymentType>>() {
            @Override
            public void onChanged(List<PaymentType> paymentTypes) {
                radioGroupPaymentMethods.removeAllViews();
                for (PaymentType method : paymentTypes) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(method.getDescription());
                    radioButton.setId(View.generateViewId());
                    radioButton.setTag(method.getId());
                    radioGroupPaymentMethods.addView(radioButton);
                }
            }
        });

        cartViewModel.getCartLiveData().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    double total = cartViewModel.getCartTotal();
                    tvTotalPurchase.setText("Total: $" + total);
                    cartAdapter.setCart(cart.getProducts());
                }
            }
        });

        purchaseViewModel.fetchPaymentMethods();

        radioGroupPaymentMethods.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                String selectedPaymentMethodDescription = selectedRadioButton.getText().toString();

                if (selectedPaymentMethodDescription.equalsIgnoreCase("Stripe")) {
                    cardInputWidget.setVisibility(View.VISIBLE);
                    example.setVisibility(View.VISIBLE);
                } else {
                    cardInputWidget.setVisibility(View.GONE);
                    example.setVisibility(View.GONE);
                }
            }
        });

        confirmPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPurchaseButton.setEnabled(false);

                int selectedId = radioGroupPaymentMethods.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String selectedPaymentMethodDescription = selectedRadioButton.getText().toString();

                    if (selectedPaymentMethodDescription.equalsIgnoreCase("Stripe")) {

                        stripeService.createPaymentMethod(cardInputWidget, (paymentMethodCreateParams, throwable) -> {
                            if (throwable != null) {
                                Toast.makeText(getContext(), "Error creating payment method: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                confirmPurchaseButton.setEnabled(true);
                                return;
                            }

                            if (paymentMethodCreateParams != null) {

                                purchaseViewModel.purchaseConfirmResponse(selectedPaymentMethodDescription);

                            }
                        });
                    } else {

                        purchaseViewModel.purchaseConfirmResponse(selectedPaymentMethodDescription);

                    }
                } else {
                    Toast.makeText(getContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
                    confirmPurchaseButton.setEnabled(true);
                }
            }
        });


        purchaseViewModel.getPurchaseLiveData().observe(getViewLifecycleOwner(), new Observer<PurchaseConfirmResponse>() {
            @Override
            public void onChanged(PurchaseConfirmResponse response) {
                confirmPurchaseButton.setEnabled(true);
                if (response != null && response.getPurchase() != null) {
                    replaceFragment(new ResumeFragment());


                } else {
                    Toast.makeText(getContext(), "No purchases information available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        purchaseViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            confirmPurchaseButton.setEnabled(true);
            Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menupurchase, menu);
    }

}
