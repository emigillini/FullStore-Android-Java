package com.example.fullstore.Activities;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fullstore.Adapter.ProductAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.Product;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends BaseFragment implements ProductAdapter.OnProductClickListener {
    private ProductAdapter productAdapter;
    private ArrayList<Product> listaproducts = new ArrayList<>();
    private TextView textView;
    private TabLayout tabLayout;

    public ProductFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        RecyclerView recyclerView = view.findViewById(R.id.rv2);
        tabLayout = view.findViewById(R.id.tab_layout);
        textView = view.findViewById(R.id.textView2);
        int spanCount = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 2 : 1;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        productAdapter = new ProductAdapter(this.listaproducts, getContext(), this);
        recyclerView.setAdapter(productAdapter);

        addCustomTab("Adidas", R.drawable.adidas);
        addCustomTab("Puma", R.drawable.puma);
        addCustomTab("Nike", R.drawable.nike);
        addCustomTab("All", R.drawable.sneaker);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab != null && tab.getText() != null) {
                    String tabName = tab.getText().toString();
                    textView.setText(tabName);

                    if (tabName.equals("All")) {
                        productViewModel.fetchAllProducts();
                    } else {
                        productViewModel.fetchProductsByBrand(tabName);
                    }
                } else {
                    textView.setText("Brand not available");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        if (getArguments() != null && getArguments().containsKey("brand_name")) {
            String brandName = getArguments().getString("brand_name");
            productViewModel.fetchProductsByBrand(brandName);
        } else {
            productViewModel.fetchAllProducts();
        }


        productViewModel.getProductListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    productAdapter.updateProductList(products);
                }
            }
        });
    }

    private void addCustomTab(String title, int icon) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        TextView tabText = tabView.findViewById(R.id.tab_title);
        ImageView tabIcon = tabView.findViewById(R.id.tab_icon);
        tabText.setText(title);
        tabIcon.setImageResource(icon);


        TabLayout.Tab tab = tabLayout.newTab().setText(title);
        tab.setCustomView(tabView);
        tabLayout.addTab(tab);
    }

    @Override
    public void onProductClick(String productId) {
        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString("productId", productId);
        productDetailFragment.setArguments(args);
        replaceFragment(productDetailFragment);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menumain, menu);
    }
}
