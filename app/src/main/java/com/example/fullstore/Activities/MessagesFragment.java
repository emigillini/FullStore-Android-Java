package com.example.fullstore.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.fullstore.Adapter.MessagesAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.AddMessageRequest;
import com.example.fullstore.models.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends BaseFragment {

    private MessagesAdapter messagesAdapter;
    private String conversationId;
    private RecyclerView recyclerView;
    private ArrayList<Message> listaMessage = new ArrayList<>();
    private EditText messageInput;
    private ImageButton sendButton;

    public MessagesFragment() {

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            conversationId = getArguments().getString("conversation_id");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        recyclerView = view.findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messagesAdapter = new MessagesAdapter(this.listaMessage);
        recyclerView.setAdapter(messagesAdapter);
        messageInput = view.findViewById(R.id.message_input);
        sendButton = view.findViewById(R.id.send_button);
        messagingViewModel.loadMessagesByConversationId(conversationId);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = messageInput.getText().toString();
                if (!content.isEmpty()) {
                    AddMessageRequest message = new AddMessageRequest(conversationId, content);
                    messagingViewModel.sendMessage(message);
                    messageInput.setText("");
                }

            }
        });

        messagingViewModel.getSessionExpiredLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSessionExpired) {
                if (isSessionExpired != null && isSessionExpired) {
                    // Mostrar la alerta de sesión expirada
                    new AlertDialog.Builder(getContext())
                            .setTitle("Session Expired")
                            .setMessage("Your session has expired. Please log in again.")
                            .setCancelable(false)
                            .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmetFrame, new LoginFragment()).addToBackStack(null).commit();
                                }
                            })
                            .show();
                }
            }
        });

        messagingViewModel.getMessagesByConversationIdLiveData(conversationId).observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menudashboard, menu);
    }


}
