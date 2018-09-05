package com.example.sam.womenintechconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    TextView name,contact,profession,role;
    Button update;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        name=(TextView)view.findViewById(R.id.tv_profilename);
        contact=(TextView)view.findViewById(R.id.tv_profile_contact);
        profession=(TextView)view.findViewById(R.id.tv_profession);
        role=(TextView)view.findViewById(R.id.tv_profilerole);
        update=(Button) view.findViewById(R.id.btn_update);

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    RegistrationData data=snapshot.getValue(RegistrationData.class);
                    if (data.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        name.setText(data.getName());
                        contact.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        profession.setText(data.getProfession());
                        role.setText(data.getRole());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(),UpdateProfile.class);
                startActivity(intent);
            }
        });

        return view;

    }
}
