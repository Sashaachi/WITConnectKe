package com.example.sam.womenintechconnect;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mToggle;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        mdrawerLayout=(DrawerLayout) findViewById(R.id.dl_drawerLayout);
        
        mToggle= new ActionBarDrawerToggle(home.this,mdrawerLayout,R.string.open,R.string.close);

        mdrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth= FirebaseAuth.getInstance();

        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_chat:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                break;
            case R.id.nav_files:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FileFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HelpFragment()).commit();
                break;

            case R.id.nav_settings:
                Toast.makeText(this,"Go to Settings",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
               logout();
                break;
        }
        mdrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(home.this,logIn.class));
    }
}
