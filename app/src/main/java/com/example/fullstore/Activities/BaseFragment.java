package com.example.fullstore.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentManager;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.R;
import com.example.fullstore.viewmodels.CartViewModel;
import com.example.fullstore.viewmodels.DashBoardViewModel;
import com.example.fullstore.viewmodels.MessagingViewModel;
import com.example.fullstore.viewmodels.ProductViewModel;
import com.example.fullstore.viewmodels.PurchaseViewModel;
import com.example.fullstore.viewmodels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseFragment extends Fragment implements MenuProvider {

    protected CartViewModel cartViewModel;
    protected SessionManager sessionManager;
    protected PurchaseViewModel purchaseViewModel;
    protected UserViewModel userViewModel;
    protected MessagingViewModel messagingViewModel;
    protected ProductViewModel productViewModel;
    protected DashBoardViewModel dashBoardViewModel;
    private boolean hasProductsInCart = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner());
        setupNavigationView();
        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {
            cartViewModel = mainActivity.getCartViewModel();
            sessionManager = mainActivity.getSessioManager();
            purchaseViewModel = mainActivity.getPurchaseViewModel();
            userViewModel = mainActivity.getUserViewModel();
            productViewModel = mainActivity.getProductViewModel();
            messagingViewModel = mainActivity.getMessagingViewModel();
            dashBoardViewModel = mainActivity.getDashBoardViewModel();
        }
        cartViewModel.getCartLiveData().observe(getViewLifecycleOwner(), cart -> {
            if (cart != null) {
                hasProductsInCart = !cart.getProducts().isEmpty();
                requireActivity().invalidateOptionsMenu();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        requireActivity().removeMenuProvider(this);
    }

    public void showToolbar(boolean show) {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && activity.getSupportActionBar() != null) {
            if (show) {
                activity.getSupportActionBar().show();
            } else {
                activity.getSupportActionBar().hide();
            }
        }
    }

    private void setupNavigationView() {
        MainActivity mainActivity = (MainActivity) requireActivity();
        DrawerLayout drawerLayout = mainActivity.getDrawerLayout();
        NavigationView navigationView = mainActivity.getNavigationView();
        BottomNavigationView bottomNavigationView = mainActivity.getBottomNavigationView();

        navigationView.setNavigationItemSelectedListener(item -> {
            String brandName = item.getTitle().toString();
            if (brandName.equals("Adidas") || brandName.equals("Puma") || brandName.equals("Nike")) {
                navigateToProductFragmentWithBrand(brandName);
            } else {
                handleNavigation(item);
            }
            drawerLayout.closeDrawers();
            return true;
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleNavigation(item);
                return false;
            }
        });
    }

    private void navigateToProductFragmentWithBrand(String brandName) {
        ProductFragment productFragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString("brand_name", brandName);
        productFragment.setArguments(args);
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(R.id.fragmetFrame, productFragment)
                .addToBackStack(null)
                .commit();
    }

    private void handleNavigation(MenuItem menuItem) {
        if (R.id.conversation == menuItem.getItemId()) {
            replaceFragment(new ConversationsFragment());

        } else if (R.id.userInfo == menuItem.getItemId()) {
            replaceFragment(new UserInfoFragment());

        } else if (R.id.purchases == menuItem.getItemId()) {
            replaceFragment(new AllResumeFragment());

        } else if (R.id.home == menuItem.getItemId() || R.id.homed == menuItem.getItemId() || R.id.homep == menuItem.getItemId() || R.id.nav_home == menuItem.getItemId() || R.id.homelist == menuItem.getItemId() || R.id.nav_homes == menuItem.getItemId()) {
            replaceFragment(new ProductFragment());

        } else if (R.id.cartd == menuItem.getItemId() || R.id.cartp == menuItem.getItemId() || R.id.cart == menuItem.getItemId() || R.id.cartm == menuItem.getItemId() || R.id.nav_carts == menuItem.getItemId()) {
            replaceFragment(new CartFragment());

        } else if (R.id.dashh == menuItem.getItemId() || R.id.dashboard == menuItem.getItemId() || R.id.nav_profile == menuItem.getItemId()) {
            replaceFragment(new DashboardFragment());

        } else if (R.id.logout == menuItem.getItemId()) {
            replaceFragment(new LogoutFragment());

        } else if (R.id.nav_back == menuItem.getItemId()) {
            FragmentManager fragmentManager = getParentFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                fragmentManager.popBackStack();
            }
        }
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {
        MainActivity.updateCartIcon(getContext(), menu, hasProductsInCart);

    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        handleNavigation(menuItem);
        return true;

    }

    protected void replaceFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_in_left,
                        R.anim.slide_out_right
                )
                .replace(R.id.fragmetFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

}