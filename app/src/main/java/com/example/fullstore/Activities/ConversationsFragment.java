package com.example.fullstore.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fullstore.Adapter.ConversationsAdapter;
import com.example.fullstore.R;
import com.example.fullstore.models.AddConversationRequest;
import com.example.fullstore.models.Conversations;

import java.util.ArrayList;
import java.util.List;

import android.widget.Button;
import android.widget.EditText;

public class ConversationsFragment extends BaseFragment implements ConversationsAdapter.OnConversationClickListener {

    private ConversationsAdapter conversationsAdapter;
    private EditText conversationNameEditText;
    private RecyclerView recyclerView;
    private Button newConversationButton;
    private ArrayList<Conversations> listConversations = new ArrayList<>();

    public ConversationsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversations, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showToolbar(true);
        recyclerView = view.findViewById(R.id.recycler_view_conversations);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        conversationsAdapter = new ConversationsAdapter(this.listConversations, this);
        recyclerView.setAdapter(conversationsAdapter);
        conversationNameEditText = view.findViewById(R.id.et_conversation_name);
        newConversationButton = view.findViewById(R.id.btn_new_conversation);

        newConversationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewConversation();
            }
        });

        messagingViewModel.loadUserConversations();

        messagingViewModel.getUserConversationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Conversations>>() {
            @Override
            public void onChanged(List<Conversations> conversations) {
                if (conversations != null) {
                    conversationsAdapter.setConversations(conversations);
                }
            }
        });
    }

    private void createNewConversation() {
        String conversationName = conversationNameEditText.getText().toString().trim();

        if (!conversationName.isEmpty()) {
            AddConversationRequest addConversationRequest = new AddConversationRequest(conversationName);
            messagingViewModel.createConversation(addConversationRequest);
            conversationNameEditText.setText("");
        } else {
            Toast.makeText(getContext(),
                    "Please enter a name for the conversation.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConversationClick(Conversations conversation) {
        Bundle bundle = new Bundle();
        bundle.putString("conversation_id", conversation.get_id());
        MessagesFragment messagesFragment = new MessagesFragment();
        messagesFragment.setArguments(bundle);
        replaceFragment(messagesFragment);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menudashboard, menu);
    }

}
