package com.example.expressclient.utils;

import android.util.Log;

import com.example.expressclient.bean.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class NetToolsTest {

    @Test
    public void registerUser() throws Exception {
        User uu = new User("lilili7","lili","1212111");
        try {
            uu = NetTools.registerUser(uu);
            Log.e("role:",uu.getRole());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("...",e.getMessage());
        }
    }
    @Test
    public void login() throws Exception{
        User uu = new User("lilili7","lili","1212111");
        try {
            uu = NetTools.loginUser(uu);
            Log.e("role:",uu.getRole());
        }catch (Exception e){
            e.printStackTrace();
            Log.e("...",e.getMessage());
        }
    }
}