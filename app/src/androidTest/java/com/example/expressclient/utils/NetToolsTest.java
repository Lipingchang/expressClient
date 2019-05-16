package com.example.expressclient.utils;

import com.example.expressclient.bean.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class NetToolsTest {

    @Test
    public void registerUser() throws Exception {
        User uu = new User("lilili","lili");
        try {
            uu = NetTools.registerUser(uu);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
        System.out.println(uu.getRole());
    }
}