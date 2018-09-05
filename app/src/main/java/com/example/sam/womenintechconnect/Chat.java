package com.example.sam.womenintechconnect;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends Activity {
    RecyclerView recyclerView;
    EditText editText;
    FloatingActionButton fab;
    ArrayList<message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView=(RecyclerView)findViewById(R.id.recyler);
        messages=new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getUid()).child(getIntent().getExtras().getString("uid")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    message m=snapshot.getValue(message.class);
                    messages.add(m);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                MessageAdapter adapter=new MessageAdapter(getApplicationContext(),messages);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        editText=(EditText)findViewById(R.id.message);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    editText.setError("enter message.");
                }
                else{
                    final message message=new message(editText.getText().toString(),getIntent().getExtras().getString("uid").toString(),FirebaseAuth.getInstance().getUid().toString());
                    FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getUid()).child(getIntent().getExtras().getString("uid")).push()
                            .setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference().child("messages").child(getIntent().getExtras().getString("uid")).child(FirebaseAuth.getInstance().getUid()).push().setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"message sent",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}
