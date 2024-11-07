package com.example.fullstore.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fullstore.Adapter.CartAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.Cart;
import com.example.fullstore.models.CartProduct;

import java.util.ArrayList;

public class CartFragment extends BaseFragment implements CartAdapter.OnProductCartClickListener {
    private RecyclerView rvCartItems;
    private CartAdapter cartAdapter;
    private ArrayList<CartProduct> arrayList = new ArrayList<>();
    private TextView tvTotalPrice;
    private Button b1;
    private LinearLayout linearLayout;

    public CartFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        b1 = view.findViewById(R.id.checkoutButton);
        linearLayout = view.findViewById(R.id.emptyCartLayout);
        rvCartItems = view.findViewById(R.id.recyclerViewCart);
        tvTotalPrice = view.findViewById(R.id.totalPriceTextView);
        cartViewModel.fetchCart();
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(this.arrayList, getContext(), this, true);
        rvCartItems.setAdapter(cartAdapter);

        cartViewModel.getCartLiveData().observe(getViewLifecycleOwner(), new Observer<Cart>() {
            @Override
            public void onChanged(Cart cart) {
                if (cart != null) {
                    Log.i("MENSAJE", "onResponse: ok" + cart);
                    if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
                        cartAdapter.setCart(cart.getProducts());
                        double total = cartViewModel.getCartTotal();
                        updateTotalPrice(total);
                        linearLayout.setVisibility(View.GONE);
                        rvCartItems.setVisibility(View.VISIBLE);
                        b1.setVisibility(View.VISIBLE);
                        tvTotalPrice.setVisibility(View.VISIBLE);
                    } else {
                        linearLayout.setVisibility(View.VISIBLE);
                        rvCartItems.setVisibility(View.GONE);
                        b1.setVisibility(View.GONE);
                        tvTotalPrice.setVisibility(View.INVISIBLE);
                    }
                } else {

                    Log.i("MENSAJE", "Null Cart");
                }
            }
        });


        cartViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            Log.i("MENSAJE", "onResponse:Error " + error);
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new PaymentFragment());
                Toast.makeText(getContext(), "Purchase ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateTotalPrice(double total) {
        tvTotalPrice.setText("Total: $" + total);
    }

    @Override
    public void onProductClick(String productId) {

        cartViewModel.removeProductFromCart(productId, 1);

    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menucart, menu);

    }

}
