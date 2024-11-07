package com.example.fullstore.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fullstore.R;
import com.example.fullstore.models.LoginResponse;
import com.example.fullstore.utils.ValidationUtils;


public class LoginFragment extends BaseFragment {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView ok, recover, register;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(false);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        ok = view.findViewById(R.id.ok);
        recover = view.findViewById(R.id.recover_l);
        register = view.findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new RegisterFragment());

            }
        });

        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new RecoverFragment());
            }
        });

        userViewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {

                    String userInfo = "User: " + loginResponse.getUser().getUsername() +
                            "\nEmail: " + loginResponse.getUser().getEmail() +
                            "\nToken: " + loginResponse.getToken() +
                            "\nIsAdmin: " + loginResponse.getUser().getIsAdmin() +
                            "\nExpires in: " + loginResponse.getExpiresIn();
                    Toast.makeText(getActivity(), "Login succesfull" + userInfo, Toast.LENGTH_SHORT).show();
                    ok.setText("" + userInfo);
                    ((MainActivity) requireActivity()).getBottomNavigationView().setVisibility(View.VISIBLE);

                    replaceFragment(new ProductFragment());
                }
            }
        });

        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {

                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                ok.setText("" + errorMessage);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (ValidationUtils.isEmailEmpty(email)) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            return;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return;
        }
        if (ValidationUtils.isEmptyPassword(password)) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        userViewModel.login(email, password);
    }


}