package com.example.expressclient.utils;

import android.icu.text.TimeZoneNames;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class tools {

    public static Date string2Date(String update) {
        int pp = update.indexOf('.');
        update = update.substring(0,pp-1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date ret = null;
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            ret = sdf.parse(update);
        }catch (Exception e){
            return new Date();
        }
        return ret;
    }
}
