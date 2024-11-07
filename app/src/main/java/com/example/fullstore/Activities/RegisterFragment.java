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

import com.example.fullstore.R;
import com.example.fullstore.utils.ValidationUtils;

public class RegisterFragment extends BaseFragment {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etAddress;
    private EditText etIdNumber;
    private Button btnRegister;
    private TextView login_r, recover_r;

    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(false);

        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etAddress = view.findViewById(R.id.et_address);
        btnRegister = view.findViewById(R.id.btn_register);
        etIdNumber = view.findViewById(R.id.editTextIdentificationNumber);
        login_r = view.findViewById(R.id.login_r);
        recover_r = view.findViewById(R.id.recover_r);

        userViewModel.getRegistrationSuccessLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean registered) {
                if (registered) {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left,
                                    R.anim.slide_in_left,
                                    R.anim.slide_out_right
                            )
                            .replace(R.id.fragmetFrame, new LoginFragment()).addToBackStack(null).commit();
                }
            }
        });

        login_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LoginFragment());

            }
        });

        recover_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new RecoverFragment());
            }
        });

        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String identification_number = etIdNumber.getText().toString().trim();

        if (ValidationUtils.isEmptyUsername(username)) {
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
            return;
        }
        if (ValidationUtils.isEmptyIdentificacion(identification_number)) {
            etIdNumber.setError("Please enter your Identification Number");
            etIdNumber.requestFocus();
            return;
        }

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
        if (ValidationUtils.isEmptyAdress(address)) {
            etAddress.setError("Please enter your address");
            etAddress.requestFocus();
            return;
        }
        if (!ValidationUtils.isValidAdress(address)) {
            etAddress.setError("Input must be at least 8 characters");
            etAddress.requestFocus();
            return;
        }
        if (!ValidationUtils.isValidIdentificacion(identification_number)) {
            etIdNumber.setError("Input must be at least 6 characters");
            etIdNumber.requestFocus();
            return;
        }
        if (!ValidationUtils.isValidUsername(username)) {
            etUsername.setError("Input must be at least 6 characters");
            etUsername.requestFocus();
            return;
        }
        if (!ValidationUtils.isValidPassword(password)) {
            etPassword.setError("Input must be at least 8 characters, contain letters and at least one number");
            etPassword.requestFocus();
            return;
        }

        userViewModel.register(username, email, password, address, Long.parseLong(identification_number));
    }
}