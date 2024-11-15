package com.example.fullstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fullstore.R;
import com.example.fullstore.models.DashboardCard;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {

    private List<DashboardCard> dashboardCards;
    private FragmentActivity activity;

    public DashboardAdapter(List<DashboardCard> dashboardCards, FragmentActivity activity) {
        this.dashboardCards = dashboardCards;
        this.activity = activity;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_card, parent, false);
        return new DashboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardViewHolder holder, int position) {
        DashboardCard card = dashboardCards.get(position);
        holder.textViewTitle.setText(card.getTitle());
        holder.imageViewIcon.setImageResource(card.getIconResource());

        // Configurar clic en cada tarjeta
        holder.itemView.setOnClickListener(v -> {
            if (activity != null) {
                try {
                    Fragment fragment = (Fragment) card.getFragmentClass().newInstance();
                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragmetFrame, fragment)
                            .addToBackStack(null)
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dashboardCards.size();
    }

    public static class DashboardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageViewIcon;

        public DashboardViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            imageViewIcon = itemView.findViewById(R.id.imageViewIcon);
        }
    }
}