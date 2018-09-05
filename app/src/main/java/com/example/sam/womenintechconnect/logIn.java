package com.example.sam.womenintechconnect;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class logIn extends AppCompatActivity {

    private EditText EmailAddress;
    private EditText Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextView forgotpswd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EmailAddress = (EditText) findViewById(R.id.et_email);
        Password = (EditText) findViewById(R.id.et_password);
        forgotpswd=findViewById(R.id.tv_forgot);
        Button loginBtn = (Button) findViewById(R.id.btn_login);
        TextView info = (TextView) findViewById(R.id.tv_info);
        Button register = (Button) findViewById(R.id.btn_register);


        //authenticate the user log in
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog =new ProgressDialog(this);


        //check if user is already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(logIn.this,home.class));
        }


        //set listener on button for log in
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if ( validate()){
                   progressDialog.setMessage("please wait");
                   progressDialog.show();

                   String Uemail = EmailAddress.getText().toString();
                   String Upassword = Password.getText().toString();



                   firebaseAuth.signInWithEmailAndPassword(Uemail, Upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               progressDialog.dismiss();

                               Toast.makeText(logIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                               FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                           RegistrationData data=snapshot.getValue(RegistrationData.class);
                                           if (data.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                               if (data.getRole().equals("Mentor")){
                                                   Intent intent=new Intent(logIn.this,Mentor.class);
                                                   startActivity(intent);

                                               }else{
                                                   startActivity((new Intent(logIn.this, home.class)));
                                               }
                                           }
                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {
                                       Toast.makeText(getApplicationContext(),""+databaseError,Toast.LENGTH_LONG).show();

                                   }
                               });

                           }
                           else{
                               progressDialog.dismiss();
                               Toast.makeText(logIn.this, "Login failed,forgot password?", Toast.LENGTH_SHORT).show();

                       }
                       }

                   });


               }
            }
        });

        //set listener on button for registration of a new user
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIn.this, Registration.class));
            }
        });
        forgotpswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIn.this,forgotPassword.class));
            }
        });

    }

    //method for listener on button Login
    private Boolean validate() {
        Boolean result=false;

        String Uemail=EmailAddress.getText().toString();
        String Upassword=Password.getText().toString();

        if( Uemail.isEmpty() || Upassword.isEmpty()){
            Toast.makeText(this,"Please enter all your details", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }



}
