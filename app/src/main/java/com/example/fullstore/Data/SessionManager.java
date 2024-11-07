package com.example.fullstore.Data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fullstore.models.PurchaseConfirmResponse;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String USER_ID = "user_id";
    private String PREF_NAME = "session_pref";
    private String AUTH_TOKEN = "auth_token";
    private String USERNAME = "username";
    private String IS_ADMIN = "is_admin";
    private String TOKEN_EXPIRATION_TIME = "token_expiration_time";
    private long TOKEN_LIFETIME = 10 * 1000 * 1000;
    private PurchaseConfirmResponse lastPurchase;


    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void saveUserId(String userId) {
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID, null);
    }

    public void saveAuthToken(String token) {
        long expirationTime = System.currentTimeMillis() + TOKEN_LIFETIME;

        editor.putString(AUTH_TOKEN, token);
        editor.putLong(TOKEN_EXPIRATION_TIME, expirationTime);
        editor.apply();
    }

    public String getAuthToken() {
        long expirationTime = sharedPreferences.getLong(TOKEN_EXPIRATION_TIME, 0);
        if (System.currentTimeMillis() > expirationTime) {
            clearSession();
            return null;
        }
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public boolean isTokenExpired() {
        long expirationTime = sharedPreferences.getLong(TOKEN_EXPIRATION_TIME, 0);
        long currentTime = System.currentTimeMillis();
        return (currentTime - expirationTime) >= TOKEN_LIFETIME;
    }

    public void clearSession() {
        editor.remove(AUTH_TOKEN);
        editor.remove(IS_ADMIN);
        editor.remove(TOKEN_EXPIRATION_TIME);
        editor.apply();
    }

    public void setIsAdmin(boolean isAdmin) {
        editor.putBoolean(IS_ADMIN, isAdmin);
        editor.apply();
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

    public boolean isAdmin() {
        return sharedPreferences.getBoolean(IS_ADMIN, false);
    }

    public void setLastPurchase(PurchaseConfirmResponse purchase) {
        this.lastPurchase = purchase;
    }

    public PurchaseConfirmResponse getLastPurchase() {
        return lastPurchase;
    }
}