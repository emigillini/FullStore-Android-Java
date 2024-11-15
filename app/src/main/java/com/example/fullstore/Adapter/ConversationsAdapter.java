package com.example.fullstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fullstore.R;
import com.example.fullstore.models.Conversations;
import java.util.List;

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ConversationViewHolder> {
    private List<Conversations> conversations;
    private final OnConversationClickListener listener;

    public ConversationsAdapter(List<Conversations> conversations, OnConversationClickListener listener) {
        this.conversations = conversations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conversation, parent, false);
        return new ConversationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationViewHolder holder, int position) {
        holder.print(position);

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public void setConversations(List<Conversations> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    public class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView conversationName, conversationData;


        public ConversationViewHolder(@NonNull View itemView) {
            super(itemView);
            conversationName = itemView.findViewById(R.id.conversation_name);
            conversationData = itemView.findViewById(R.id.conversation_descriptions);
        }


        public void print(int position) {
            Conversations conversation = conversations.get(position);
            conversationName.setText(conversation.getName());
            conversationData.setText("Select Conversation");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onConversationClick(conversation);
                }
            });

        }
    }

    public interface OnConversationClickListener {
        void onConversationClick(Conversations conversation);
    }
}
