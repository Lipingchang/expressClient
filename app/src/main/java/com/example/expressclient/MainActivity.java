package com.example.expressclient;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity
implements
        nav_home.OnFragmentInteractionListenerNavHome,
        nav_newSend.OnFragmentInteractionListenerNewSend,
        nav_userCenter.OnFragmentInteractionListenerNavUserCenter
{

    private TextView mTextMessage;
    // 路由控制器
    private NavController navController;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    mTextMessage.setText(R.string.nav_home);
                    navController.navigate(R.id.nav_home);
                    return true;
                case R.id.nav_newSend:
                    mTextMessage.setText(R.string.nav_newSend);
                    navController.navigate(R.id.nav_newSend);
                    return true;
                case R.id.nav_userCenter:
                    mTextMessage.setText(R.string.nav_userCenter);
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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.navController = Navigation.findNavController(this, R.id.my_nav_host_fragment);

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
