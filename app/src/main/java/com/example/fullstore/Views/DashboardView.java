package com.example.fullstore.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.fullstore.R;
import com.example.fullstore.models.DashboardCard;

import java.util.List;

public class DashboardView extends LinearLayout {

    private FragmentActivity activity;

    public DashboardView(Context context) {
        super(context);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
    }

    public void setFragmentActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    public void setDashboardCards(List<DashboardCard> dashboardCards) {
        removeAllViews();

        for (DashboardCard card : dashboardCards) {
            View cardView = LayoutInflater.from(getContext()).inflate(R.layout.item_dashboard_card, this, false);

            TextView textViewTitle = cardView.findViewById(R.id.textViewTitle);
            ImageView imageViewIcon = cardView.findViewById(R.id.imageViewIcon);

            textViewTitle.setText(card.getTitle());
            imageViewIcon.setImageResource(card.getIconResource());

            cardView.setOnClickListener(v -> {
                if (activity != null) {
                    try {
                        Fragment fragment = (Fragment) card.getFragmentClass().newInstance();
                        activity.getSupportFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(
                                        R.anim.slide_in_right,
                                        R.anim.slide_out_left,
                                        R.anim.slide_in_left,
                                        R.anim.slide_out_right
                                )
                                .replace(R.id.fragmetFrame, fragment)
                                .addToBackStack(null)
                                .commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            addView(cardView);
        }
    }
}