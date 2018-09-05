package com.example.sam.womenintechconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    ArrayList<message> arrayList;
    View view;

    public MessageAdapter(Context context, ArrayList<message> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);

        MessageAdapter.ViewHolder holder = new MessageAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final message message=arrayList.get(position);
        holder.message.setText(message.getText());
        holder.time.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss a", message.getDate()));
        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    RegistrationData data=snapshot.getValue(RegistrationData.class);
                    if (data.getId().equals(message.getFrom())){
                        holder.from.setText(data.getName());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView message,time,from;
        public ViewHolder(View itemView) {
            super(itemView);
            message=(TextView)itemView.findViewById(R.id.message);
            time=(TextView)itemView.findViewById(R.id.time);
            from=(TextView)itemView.findViewById(R.id.from);



        }

    }
}
