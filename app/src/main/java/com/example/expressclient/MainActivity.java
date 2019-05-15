package com.example.expressclient;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity
implements
        nav_home.OnFragmentInteractionListenerNavHome,
        nav_newSend.OnFragmentInteractionListenerNewSend,
        nav_userCenter.OnFragmentInteractionListenerNavUserCenter
{

    // 路由控制器
    private NavController navController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    navController.navigate(R.id.nav_home);
                    return true;
                case R.id.nav_newSend:
                    navController.navigate(R.id.nav_newSend);
                    return true;
                case R.id.nav_userCenter:
                    navController.navigate(R.id.nav_userCenter);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
