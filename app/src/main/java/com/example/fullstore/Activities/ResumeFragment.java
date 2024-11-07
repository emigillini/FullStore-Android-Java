package com.example.fullstore.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fullstore.R;

import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fullstore.models.PurchaseConfirmResponse;

public class ResumeFragment extends BaseFragment {

    private TextView purchaseIdTextView;
    private TextView purchaseDateTextView;
    private TextView totalAmountTextView;
    private TextView purchasedItemsTextView;
    private Button button;

    public ResumeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        purchaseIdTextView = view.findViewById(R.id.purchaseIdTextView);
        purchaseDateTextView = view.findViewById(R.id.purchaseDateTextView);
        totalAmountTextView = view.findViewById(R.id.totalAmountTextView);
        purchasedItemsTextView = view.findViewById(R.id.purchasedItemsTextView);
        cartViewModel.fetchCart();
        button = view.findViewById(R.id.purchases_pu);
        PurchaseConfirmResponse lastPurchase = sessionManager.getLastPurchase();

        if (lastPurchase != null) {
            purchaseIdTextView.setText("Purchase ID: " + lastPurchase.getPurchase().getId());
            purchaseDateTextView.setText("Date: " + lastPurchase.getPurchase().getFormattedDate());
            totalAmountTextView.setText("Total Amount: $" + lastPurchase.getPurchase().getTotal());
            purchasedItemsTextView.setText("Thanks a Lot for Your Purchase! We Hope You Love Your New Items!");

        }

        button.setOnClickListener(view1 ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_right,
                                R.anim.slide_out_left,
                                R.anim.slide_in_left,
                                R.anim.slide_out_right
                        )
                        .replace(R.id.fragmetFrame, new AllResumeFragment()).commit());
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menupurchase, menu);
    }


}
