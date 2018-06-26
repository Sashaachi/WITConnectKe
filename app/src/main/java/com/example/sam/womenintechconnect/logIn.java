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


public class logIn extends AppCompatActivity {

    private EditText EmailAddress;
    private EditText Password;
    private Button LoginBtn;
    private TextView Info;
    private Button Register;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EmailAddress = (EditText) findViewById(R.id.et_email);
        Password = (EditText) findViewById(R.id.et_password);
        LoginBtn = (Button) findViewById(R.id.btn_login);
        Info = (TextView) findViewById(R.id.tv_info);
        Register = (Button) findViewById(R.id.btn_register);

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
        LoginBtn.setOnClickListener(new View.OnClickListener() {
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
                               startActivity((new Intent(logIn.this, home.class)));

                           }
                           else{
                               progressDialog.dismiss();
                               Toast.makeText(logIn.this, "Login failed", Toast.LENGTH_SHORT).show();

                       }
                       }

                   });


               }
            }
        });

        //set listener on button for registration of a new user
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(logIn.this, Registration.class));
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
