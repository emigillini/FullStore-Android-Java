package com.example.fullstore.utils;

import android.text.TextUtils;

public class ValidationUtils {


    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }

    public static boolean isEmailEmpty(String email) {
        return TextUtils.isEmpty(email);
    }

    public static boolean isEmptyIdentificacion(String identificacion) {
        return TextUtils.isEmpty(identificacion);
    }

    public static boolean isEmptyPassword(String password) {
        return TextUtils.isEmpty(password);
    }

    public static boolean isEmptyAdress(String adress) {
        return TextUtils.isEmpty(adress);
    }

    public static boolean isEmptyUsername(String username) {
        return TextUtils.isEmpty(username);
    }

    public static boolean isValidAdress(String adress) {

        return adress != null && adress.length() >= 8;
    }

    public static boolean isValidIdentificacion(String identification) {

        return identification != null && identification.length() >= 6;
    }

    public static boolean isValidUsername(String username) {

        return username != null && username.length() >= 6;
    }

    public static boolean isValidPassword(String password) {

        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        return password.matches(pattern);
    }

}