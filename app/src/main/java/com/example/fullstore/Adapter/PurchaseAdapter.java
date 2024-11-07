package com.example.fullstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fullstore.R;
import com.example.fullstore.models.SimplePurchase;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder> {

    private List<SimplePurchase> purchaseList;
    private Context context;

    public PurchaseAdapter(List<SimplePurchase> purchaseList, Context context) {
        this.purchaseList = purchaseList;
        this.context = context;
    }

    @NonNull
    @Override
    public PurchaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchase, parent, false);
        return new PurchaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseViewHolder holder, int position) {
        SimplePurchase simplePurchase = purchaseList.get(position);
        holder.totalTextView.setText("Total: $" + simplePurchase.getTotal());
        holder.invoiceTextView.setText("Invoice: " + simplePurchase.getInvoice_number());
        holder.dateTextView.setText("Date: " + simplePurchase.getDate());
        holder.userTextView.setText("User: " + simplePurchase.getDelivery().getDelivery_status()); // Ajustar formato si es necesario
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    static class PurchaseViewHolder extends RecyclerView.ViewHolder {
        TextView totalTextView, invoiceTextView, dateTextView, userTextView;

        public PurchaseViewHolder(@NonNull View itemView) {
            super(itemView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            invoiceTextView = itemView.findViewById(R.id.invoiceTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            userTextView = itemView.findViewById(R.id.userTextView);
        }
    }

    public void setPurchaseList(List<SimplePurchase> purchases) {
        this.purchaseList = purchases;
        notifyDataSetChanged();
    }
}
