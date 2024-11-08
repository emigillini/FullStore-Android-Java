package com.example.fullstore.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fullstore.Data.SessionManager;
import com.example.fullstore.R;
import com.example.fullstore.Views.DashboardView;
import com.example.fullstore.viewmodels.CartViewModel;
import com.example.fullstore.viewmodels.DashBoardViewModel;
import com.example.fullstore.viewmodels.MessagingViewModel;
import com.example.fullstore.viewmodels.ProductViewModel;
import com.example.fullstore.viewmodels.PurchaseViewModel;
import com.example.fullstore.viewmodels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private CartViewModel cartViewModel;
    private MessagingViewModel messagingViewModel;
    private UserViewModel userViewModel;
    private ProductViewModel productViewModel;
    private PurchaseViewModel purchaseViewModel;
    private DashBoardViewModel dashBoardViewModel;
    private boolean hasProductsInCart = false;
    private SessionManager sessionManager;

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public CartViewModel getCartViewModel() {
        return cartViewModel;
    }

    public SessionManager getSessioManager() {
        return sessionManager;
    }

    public PurchaseViewModel getPurchaseViewModel() {
        return purchaseViewModel;
    }

    public ProductViewModel getProductViewModel() {
        return productViewModel;
    }

    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public MessagingViewModel getMessagingViewModel() {
        return messagingViewModel;
    }

    public DashBoardViewModel getDashBoardViewModel() {
        return dashBoardViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(drawerLayout, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            drawerLayout.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom);
            findViewById(R.id.toolbar_container).setPadding(0, systemBars.top, 0, 0);
            findViewById(R.id.fragmetFrame).setPadding(0, 0, 0, systemBars.bottom);

            return insets;
        });
        sessionManager = SessionManager.getInstance(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.setSessionManager(sessionManager, this);
        purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        purchaseViewModel.setSessionManager(sessionManager, this);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.setSessionManager(sessionManager, this);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setSessionManager(sessionManager, this);
        messagingViewModel = new ViewModelProvider(this).get(MessagingViewModel.class);
        messagingViewModel.setSessionManager(sessionManager, this);
        dashBoardViewModel = new ViewModelProvider(this).get(DashBoardViewModel.class);
        dashBoardViewModel.setSessionManager(sessionManager, this);
        cartViewModel.fetchCart();
        observeCartChanges();
        observeSessionExpiration();
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setVisibility(View.GONE);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri data = intent.getData();
            if (data != null) {
                String uid = data.getPathSegments().get(1);
                String token = data.getPathSegments().get(2);

                if (uid != null && token != null) {
                    ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                    Bundle args = new Bundle();
                    args.putString("uid", uid);
                    args.putString("token", token);
                    resetPasswordFragment.setArguments(args);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmetFrame, resetPasswordFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        }
    }

    private void observeSessionExpiration() {
        dashBoardViewModel.getSessionExpiredLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    sessionManager.clearSession();
                    showSessionExpiredDialog();
                }
            }
        });

        cartViewModel.getSessionExpiredLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    sessionManager.clearSession();
                    showSessionExpiredDialog();

                }
            }
        });
        messagingViewModel.getSessionExpiredLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    sessionManager.clearSession();
                    showSessionExpiredDialog();

                }
            }
        });
        purchaseViewModel.getSessionExpiredLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    sessionManager.clearSession();
                    showSessionExpiredDialog();

                }
            }
        });

    }

    private void showSessionExpiredDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Session Expired")
                .setMessage("Your session has expired. Please log in again.")
                .setCancelable(false)
                .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirigir a AuthActivity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    private void observeCartChanges() {
        cartViewModel.getCartLiveData().observe(this, cart -> {
            if (cart != null) {
                hasProductsInCart = !cart.getProducts().isEmpty();
                invalidateOptionsMenu();
                updateBottomNavigationViewIcons(hasProductsInCart);
            }
        });
    }

    private void updateBottomNavigationViewIcons(boolean hasProductsInCart) {
        Menu menu = bottomNavigationView.getMenu();

        MenuItem cartMenuItem = menu.findItem(R.id.nav_carts);
        if (cartMenuItem != null) {
            if (hasProductsInCart) {
                cartMenuItem.setIcon(R.drawable.cart_shopping_solid);
            } else {
                cartMenuItem.setIcon(R.drawable.cart);
            }
        }
    }

    protected static void updateCartIcon(Context context, Menu menu, boolean hasProductsInCart) {

        int[] cartMenuIds = {
                R.id.cartd,
                R.id.cartp,
                R.id.cart,
                R.id.cartm,
                R.id.cartc,
                R.id.nav_carts
        };

        for (int id : cartMenuIds) {
            MenuItem iconItem = menu.findItem(id);
            if (iconItem != null) {
                if (hasProductsInCart) {
                    Drawable cartIcon = context.getResources().getDrawable(R.drawable.cart_solid, null);
                    if (cartIcon != null) {
                        cartIcon.setTint(context.getResources().getColor(R.color.red, null));
                        iconItem.setIcon(cartIcon);
                    } else {
                        iconItem.setIcon(R.drawable.cart);
                    }
                }
            }
        }
    }


}




