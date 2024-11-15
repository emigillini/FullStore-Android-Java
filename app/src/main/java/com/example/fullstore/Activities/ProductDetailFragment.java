package com.example.fullstore.Activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.fullstore.R;
import com.example.fullstore.models.Product;

public class ProductDetailFragment extends BaseFragment {

    private TextView productName, productDescription, productPrice;
    private ImageView productImage;
    private String productId;

    public ProductDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            productId = getArguments().getString("productId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);

        productName = view.findViewById(R.id.tvProductName);
        productDescription = view.findViewById(R.id.tvProductDescription);
        productPrice = view.findViewById(R.id.tvProductPrice);
        productImage = view.findViewById(R.id.ivProductImage);
        Button add = view.findViewById(R.id.add_b);
        Button remove = view.findViewById(R.id.remove_b);
        cartViewModel.fetchCart();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.addProductToCart(productId, 1);
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.removeProductFromCart(productId, 1);
                Toast.makeText(getActivity(), "Remove", Toast.LENGTH_SHORT).show();
                Log.i("VER", "p" + productId);
            }
        });

        if (productId != null) {
            productViewModel.fetchProductById(productId);
        }

        productViewModel.getProductLiveData().observe(getViewLifecycleOwner(), new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    productName.setText(product.getModel());
                    productDescription.setText(product.getDetail());
                    productPrice.setText("Price: " + product.getPrice());
                    Glide.with(requireContext())
                            .load(product.getImage())
                            .into(productImage);
                }
            }
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menumain, menu);
    }
}
