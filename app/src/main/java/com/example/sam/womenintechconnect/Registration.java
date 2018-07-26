package com.example.sam.womenintechconnect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {

    private EditText UserName;
    private EditText Email;
    private EditText Password;
    private FirebaseAuth firebaseAuth;
    private EditText confirmPswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        UserName= findViewById(R.id.et_username);
        Email= findViewById(R.id.et_email);
        Password= findViewById(R.id.et_password);
        confirmPswd=findViewById(R.id.et_password2);
        Button signUp = findViewById(R.id.btn_sign_up);



        //create an instance of firebase authentication
        firebaseAuth =FirebaseAuth.getInstance();


        //listener on sign up button validating and uploading data to the database
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate user details
               if( validate() ){
                   String Uemail=Email.getText().toString().trim();
                   String Upassword=Password.getText().toString().trim();

                    //upload data to database
                   firebaseAuth.createUserWithEmailAndPassword(Uemail,Upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if (task.isSuccessful()) {
                               Toast.makeText(Registration.this, "Registration successful", Toast.LENGTH_SHORT).show();

                               startActivity(new Intent(Registration.this,logIn.class));

                           } else {
                               Toast.makeText(Registration.this,"Registration Failed ",Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
            }
        });


    }

    public Boolean validate(){
        Boolean result=false;

        String Uname=UserName.getText().toString();
        String Uemail=Email.getText().toString();
        String Upassword=Password.getText().toString();
        String Uconfirm=confirmPswd.getText().toString();

        if(Uname.isEmpty() || Uemail.isEmpty() || Upassword.isEmpty() || Uconfirm.isEmpty()){
            Toast.makeText(this,"Please enter all your details", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;
        }
        return result;
    }


}
