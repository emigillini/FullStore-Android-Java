package com.example.fullstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.fullstore.R;
import com.example.fullstore.models.CartProduct;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartProduct> products;
    private Context context;
    private OnProductCartClickListener listener;
    private boolean showRemoveButton;

    public CartAdapter(List<CartProduct> products, Context context, OnProductCartClickListener listener, boolean showRemoveButton) {
        this.products = products;
        this.context = context;
        this.listener = listener;
        this.showRemoveButton = showRemoveButton;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.imprimir(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setCart(List<CartProduct> newProducts) {
        this.products.clear();
        this.products.addAll(newProducts);
        notifyDataSetChanged();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;
        ImageView iv1;
        Button button;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tvProductName);
            tv2 = itemView.findViewById(R.id.tvProductPrice);
            tv3 = itemView.findViewById(R.id.tvProductQuantity);
            iv1 = itemView.findViewById(R.id.ivProductImage);
            button = itemView.findViewById(R.id.buttonRemove);

        }

        public void imprimir(int position) {
            CartProduct product = products.get(position);
            tv1.setText(product.getProduct().getModel());
            tv2.setText("Description: " + product.getProduct().getDetail());
            tv3.setText(("Quantity: " + product.getQuantity()));

            Glide.with(itemView.getContext())
                    .load(product.getProduct().getImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(iv1);
            if (showRemoveButton) {
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onProductClick(product.getProduct().getId());
                    }
                });
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    public interface OnProductCartClickListener {
        void onProductClick(String productId);
    }
}