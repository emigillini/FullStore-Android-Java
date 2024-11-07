package com.example.fullstore.Activities;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fullstore.R;
import com.example.fullstore.models.UpdateUserRequest;


public class UserInfoFragment extends BaseFragment {

    private EditText editTextIdentificationNumber, editTextPhone, editTextAddress;
    private Button buttonUpdate;

    public UserInfoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);

        editTextIdentificationNumber = view.findViewById(R.id.editTextIdentificationNumber);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);

        dashBoardViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                editTextIdentificationNumber.setText(String.valueOf(user.getIdentificationNumber())); // Conversión a String
                editTextPhone.setText(user.getPhone());
                editTextAddress.setText(user.getAdress());
            }
        });

        dashBoardViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        buttonUpdate.setOnClickListener(v -> {
            UpdateUserRequest updateUserRequest = new UpdateUserRequest();

            if (!editTextIdentificationNumber.getText().toString().isEmpty()) {
                updateUserRequest.setIdentification_number(Long.parseLong(editTextIdentificationNumber.getText().toString()));
            }

            if (!editTextPhone.getText().toString().isEmpty()) {
                updateUserRequest.setPhone(editTextPhone.getText().toString());
            }
            if (!editTextAddress.getText().toString().isEmpty()) {
                updateUserRequest.setAdress(editTextAddress.getText().toString());
            }

            dashBoardViewModel.updateUser(updateUserRequest);

        });

        dashBoardViewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                Toast.makeText(getContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error updating data", Toast.LENGTH_SHORT).show();
            }
        });

        dashBoardViewModel.getUserData();
    }


    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menudashboard, menu);
    }


}
