package com.example.fullstore.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fullstore.R;
import com.example.fullstore.utils.ValidationUtils;


public class RecoverFragment extends BaseFragment {
    private EditText etEmail;
    private TextView tvLogin;
    private Button btnRequestReset;

    public RecoverFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_recover, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(false);
        etEmail = view.findViewById(R.id.et_email);
        tvLogin = view.findViewById(R.id.login_re);
        btnRequestReset = view.findViewById(R.id.btn_request_reset);

        userViewModel.getPasswordResponseLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String responseMessage) {
                if (responseMessage != null) {
                    Toast.makeText(getActivity(), "Mail Send", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getActivity(), "errorMessage" + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new LoginFragment());
            }
        });

        btnRequestReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRequestReset();
            }

            private void attemptRequestReset() {
                String email = etEmail.getText().toString().trim();
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
                userViewModel.requestPasswordReset(email);
                replaceFragment(new LoginFragment());

            }
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menumain, menu);
    }


}