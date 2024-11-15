package com.example.fullstore.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.example.fullstore.R;
import com.example.fullstore.models.NewPasswordRequest;


public class ResetPasswordFragment extends BaseFragment {
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private String uid;
    private String token;

    public ResetPasswordFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);

        if (getArguments() != null) {
            uid = getArguments().getString("uid");
            token = getArguments().getString("token");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        etNewPassword = view.findViewById(R.id.et_new_password);
        etConfirmPassword = view.findViewById(R.id.et_confirm_password);
        Button btnResetPassword = view.findViewById(R.id.btn_reset_password);


        if (getArguments() != null) {
            uid = getArguments().getString("uid");
            token = getArguments().getString("token");
        }

        userViewModel.getPasswordResponseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String responseMessage) {
                if (responseMessage != null) {
                    Toast.makeText(getContext(), "Password successfully reset", Toast.LENGTH_SHORT).show();

                }
            }
        });
        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptResetPassword();
            }


            private void attemptResetPassword() {
                String newPassword = etNewPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword)) {
                    etNewPassword.setError("Please enter a new password");
                    etNewPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    etConfirmPassword.setError("Please confirm your new password");
                    etConfirmPassword.requestFocus();
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    etConfirmPassword.setError("Passwords do not match");
                    etConfirmPassword.requestFocus();
                    return;
                }

                NewPasswordRequest newPasswordRequest = new NewPasswordRequest(newPassword, confirmPassword);
                userViewModel.resetPassword(uid, token, newPasswordRequest);
            }
        });

    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menumain, menu);
    }

}