package com.example.sam.womenintechconnect;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class RecylerViewAdapter  extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder>  {
     Context context;
     ArrayList<RegistrationData> data;
     View view;

    public RecylerViewAdapter(Context context, ArrayList<RegistrationData> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);

        RecylerViewAdapter.ViewHolder holder = new RecylerViewAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      final RegistrationData item=data.get(position);
      holder.name.setText(item.getName());
      holder.email.setText(item.getEmail());
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(context,Chat.class);
              i.putExtra("uid",item.getId());
              view.getContext().startActivity(i);
          }
      });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
     TextView name,email;
    public ViewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.name);
        email=(TextView)itemView.findViewById(R.id.email);


    }

}

}
