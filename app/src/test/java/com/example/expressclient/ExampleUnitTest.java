package com.example.expressclient;

import com.example.expressclient.utils.Constant;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static okhttp3.internal.Util.UTC;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Test
    public void addition_isCorrect() throws Exception{
        String update = "2019-05-15T11:07:49.576+0000";
        int pp = update.indexOf('.');
        update = update.substring(0,pp-1);

        SimpleDateFormat sdf = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);
        Date ret = sdf.parse(update);

        System.out.println( sdf.getTimeZone());
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(ret));
        System.out.println(ret);


    }
}