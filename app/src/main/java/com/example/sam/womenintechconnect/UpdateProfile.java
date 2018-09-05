package com.example.sam.womenintechconnect;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {
    private EditText newName,newContact,newProfession;
    private Button save;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        newContact=(EditText) findViewById(R.id.et_Newcontact);
        newName=(EditText) findViewById(R.id.et_Newname);
        newProfession=(EditText) findViewById(R.id.et_Newprofession);
        save=(Button) findViewById(R.id.btn_save);
        progressDialog =new ProgressDialog(getApplicationContext());

        FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    RegistrationData data=snapshot.getValue(RegistrationData.class);
                    if (data.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        progressDialog.setMessage("please wait");
                        progressDialog.show();
                        newName.setText(data.getName());
                        newContact.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        newProfession.setText(data.getProfession());
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("please wait");
                progressDialog.show();
                String name=newName.getText().toString();
                String email=newContact.getText().toString();
                String profession=newProfession.getText().toString();

                RegistrationData data=new RegistrationData(name,email,profession);

                FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            RegistrationData data=snapshot.getValue(RegistrationData.class);

                            if (data.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){

                                snapshot.getRef().removeValue();
                                FirebaseDatabase.getInstance().getReference().child("users").push().setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"profile updated",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),""+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
