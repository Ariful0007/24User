package com.meass.a24user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.myView> {
    private List<Notification_Model> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    public NotificationAdapter(List<Notification_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NotificationAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbox, parent, false);
        return new NotificationAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.myView holder, final int position) {
        holder.add_notes_title.setText(data.get(position).getUsername());
        holder.blog_detail_desc.setText(data.get(position).getFeedback());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView blog_detail_desc,add_notes_title;

        public myView(@NonNull View itemView) {

            super(itemView);
            blog_detail_desc=itemView.findViewById(R.id.blog_detail_desc);
            add_notes_title=itemView.findViewById(R.id.add_notes_title);

        }
    }
}
