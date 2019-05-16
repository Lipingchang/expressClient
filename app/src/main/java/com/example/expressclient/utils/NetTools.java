package com.example.expressclient.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.expressclient.bean.User;

import org.json.JSONObject;

import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.expressclient.utils.tools.string2Date;

public class NetTools {
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    private static Context context;
    public static void initNetTool (Context context){
        NetTools.context = context;
    }

    public static User loadCachedUser(){
        SharedPreferences pref = context.getSharedPreferences(Constant.sharedP_USER_INFO ,Context.MODE_PRIVATE);
        if (pref == null)
            return null;
        int userid = pref.getInt(Constant.sharedP_USER_ID,-1);
        String username = pref.getString(Constant.sharedP_USER_NAME,null);
        String password = pref.getString(Constant.sharedP_USER_PWD,null);
        String role = pref.getString(Constant.sharedP_USER_ROLE,null);
        String tel = pref.getString(Constant.sharedP_USER_TELPHONE,null);
        Date createTime = new Date(pref.getLong(Constant.sharedP_USER_CREATE_TIME,0));
        Date updateTime = new Date(pref.getLong(Constant.sharedP_USER_UPDATE_TIME,0));
        if ( userid == -1 || username == null || password == null || role == null || createTime.getTime() == 0 || updateTime.getTime() == 0)
            return null;
        User uu = new User(userid,username,tel,password,role,createTime,updateTime);
        return uu;
    }
    public static boolean cacheUser(User uu){
        int userid = uu.getUserid();
        String username = uu.getUsername();
        String password = uu.getPassword();
        String role = uu.getRole();
        Date createTime = uu.getCreateTime();
        Date updateTime = uu.getUpdateTime();

        SharedPreferences.Editor editor = null;
        try {
            SharedPreferences pref = context.getSharedPreferences(Constant.sharedP_USER_INFO, Context.MODE_PRIVATE);
            editor = pref.edit();
            editor.putInt(Constant.sharedP_USER_ID,userid);
            editor.putString(Constant.sharedP_USER_NAME,username);
            editor.putString(Constant.sharedP_USER_PWD,password);
            editor.putString(Constant.sharedP_USER_ROLE,role);
            editor.putLong(Constant.sharedP_USER_CREATE_TIME,createTime.getTime());
            editor.putLong(Constant.sharedP_USER_UPDATE_TIME,updateTime.getTime());
            editor.commit();
        }catch (Exception e){
            if ( editor != null )
                editor.clear();
            return false;
        }
        return true;
    }

    public static User registerUser(User newUser) throws Exception{
        OkHttpClient client = new OkHttpClient();
        JSONObject juser = new JSONObject();
        juser.put(Constant.api_username,newUser.getUsername());
        juser.put(Constant.api_password,newUser.getPassword());
        String load = juser.toString();

        RequestBody body = RequestBody.create(JSON,load);
        Request request = new Request.Builder()
                .url(Constant.apiRegister)
                .post(body)
                .build();

        User ret = null;
        try {
            Response response = client.newCall(request).execute();
            juser = new JSONObject( response.body().string() );
            String data = (String)juser.get(Constant.apiBodyData);
            juser = new JSONObject(data);
            int id = juser.getInt(Constant.apiBody_userid);
            String name = juser.getString(Constant.apiBody_username);
            String pwd = juser.getString(Constant.apiBody_password);
            String role = juser.getString(Constant.apiBody_role);
            Date create = string2Date(juser.getString(Constant.apiBody_createtime));
            Date update = string2Date(juser.getString(Constant.apiBody_updatetime));
            String telphone = juser.getString(Constant.apiBody_updatetime);

            ret.setUserid(id);
            ret.setUsername(name);
            ret.setPassword(pwd);
            ret.setRole(role);
            ret.setCreateTime(create);
            ret.setUpdateTime(update);

            return ret;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}