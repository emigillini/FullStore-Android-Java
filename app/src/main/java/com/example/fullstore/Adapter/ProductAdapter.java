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
import com.example.fullstore.models.Product;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnProductClickListener listener;


    public ProductAdapter(List<Product> productList, Context context, OnProductClickListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.print(position);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void updateProductList(List<Product> newProducts) {
        productList.clear();
        productList.addAll(newProducts);
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2, tv3;
        ImageView iv1;
        Button button;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            iv1 = itemView.findViewById(R.id.iv1);
            button = itemView.findViewById(R.id.goToDetail);
        }

        public void print(int position) {
            Product product = productList.get(position);
            tv1.setText(product.getModel());
            tv2.setText("Description: " + product.getDetail());
            tv3.setText("Price: " + product.getPrice());

            Glide.with(itemView.getContext())
                    .load(product.getImage())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(iv1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProductClick(product.get_id());

                }
            });
        }

    }

    public interface OnProductClickListener {
        void onProductClick(String productId);
    }


}