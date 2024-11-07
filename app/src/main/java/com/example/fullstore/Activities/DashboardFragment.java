package com.example.fullstore.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fullstore.R;
import com.example.fullstore.Views.DashboardView;
import com.example.fullstore.models.DashboardCard;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends BaseFragment {

    public DashboardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        DashboardView dashboardView = view.findViewById(R.id.dashboardView);
        FragmentActivity activity = requireActivity();

        if (activity != null) {
            dashboardView.setFragmentActivity(activity);
        }
        List<DashboardCard> dashboardCards = crearTarjetasDashboard();
        dashboardView.setDashboardCards(dashboardCards);

    }

    private List<DashboardCard> crearTarjetasDashboard() {
        List<DashboardCard> cards = new ArrayList<>();
        cards.add(new DashboardCard("Home", R.drawable.house_solid, ProductFragment.class));
        cards.add(new DashboardCard("User Information", R.drawable.usericon, UserInfoFragment.class));
        cards.add(new DashboardCard("Conversations", R.drawable.conversations, ConversationsFragment.class));
        cards.add(new DashboardCard("My Purchases", R.drawable.cart, AllResumeFragment.class));
        return cards;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menudashboard, menu);
    }
}
