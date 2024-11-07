package com.example.fullstore.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fullstore.Adapter.PurchaseAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.SimplePurchase;


import java.util.ArrayList;
import java.util.List;

public class AllResumeFragment extends BaseFragment {

    private RecyclerView purchaseRecyclerView;
    private PurchaseAdapter purchaseAdapter;
    private ArrayList<SimplePurchase> purchaseArrayList = new ArrayList<>();

    public AllResumeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_resume, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        purchaseRecyclerView = view.findViewById(R.id.purchase_recycler_view);
        purchaseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        purchaseAdapter = new PurchaseAdapter(this.purchaseArrayList, getContext()); // Inicializa el adaptador vacío
        purchaseRecyclerView.setAdapter(purchaseAdapter);
        cartViewModel.fetchCart();

        purchaseViewModel.getSessionExpiredLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {

                    new AlertDialog.Builder(getContext())
                            .setTitle("Sesión expirada")
                            .setMessage("Tu sesión ha expirado. Por favor, inicia sesión nuevamente.")
                            .setCancelable(false)
                            .setPositiveButton("Iniciar sesión", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmetFrame, new LoginFragment()).addToBackStack(null).commit();
                                }
                            })
                            .show();
                }
            }
        });

        purchaseViewModel.getPurchaseListLiveData().observe(getViewLifecycleOwner(), new Observer<List<SimplePurchase>>() {
            @Override
            public void onChanged(List<SimplePurchase> purchases) {
                if (purchases != null) {
                    purchaseAdapter.setPurchaseList(purchases);
                }
            }
        });

        purchaseViewModel.fetchUserPurchases();
    }


    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menudashboard, menu);
    }

}