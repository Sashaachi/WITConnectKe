package com.example.sam.womenintechconnect;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.jar.Attributes;

public class Registration extends AppCompatActivity {

    private EditText UserName;
    private EditText Email;
    private EditText Profession;
    private EditText Password;
    private FirebaseAuth firebaseAuth;
    private EditText confirmPswd;
    String Uname,Uemail,Uprofession,selectedItem;
    private Spinner RoleSpinner;
    FirebaseUser mCurrentUser;
    DatabaseReference myRef;
    FirebaseDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        UserName= findViewById(R.id.et_username);
        Email= findViewById(R.id.et_email);
        Profession=findViewById(R.id.et_profession);
        RoleSpinner=findViewById(R.id.role_spinner);
        Password= findViewById(R.id.et_password);
        confirmPswd=findViewById(R.id.et_password2);
        Button signUp = findViewById(R.id.btn_sign_up);

        //create an array adapter for the spinner to be loaded with data
        ArrayAdapter<String> roleAdapter= new ArrayAdapter<String>(Registration.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Role));
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RoleSpinner.setAdapter(roleAdapter);

        RoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                selectedItem = RoleSpinner.getSelectedItem().toString();
                //selectedItem = selectedItem.equals("All Types")?selectedItem.replaceAll(" ", "").toLowerCase():selectedItem;

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }});

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
                    //authenticate users
                   firebaseAuth.createUserWithEmailAndPassword(Uemail,Upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {

                           if (task.isSuccessful()) {
                               mCurrentUser=firebaseAuth.getCurrentUser();
                               SendUserData();
                               Toast.makeText(Registration.this, "Registration successful", Toast.LENGTH_SHORT).show();


                                    finish();
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

         Uname=UserName.getText().toString();
         Uemail=Email.getText().toString();
         Uprofession=Profession.getText().toString();
        String Upassword=Password.getText().toString();
        String Uconfirm=confirmPswd.getText().toString();

        if(Uname.isEmpty() || Uemail.isEmpty() ||Uprofession.isEmpty()|| Upassword.isEmpty() || Uconfirm.isEmpty()){
            Toast.makeText(this,"Please enter all your details", Toast.LENGTH_SHORT).show();
        }
        else{
            result=true;

        }
        return result;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void SendUserData() {
        //firebase db instance
         myDatabase= FirebaseDatabase.getInstance();
         myDatabase.getReference("UserProfile");
        //reference to the Firebase database with the users node
         myRef=myDatabase.getReference("Users");
       //usersData object
         RegistrationData userData= new RegistrationData(Uname,Uemail,Uprofession,selectedItem);
         //push data to users node
         myRef.child(mCurrentUser.getUid()).setValue(userData);


    }


}
