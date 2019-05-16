package com.example.expressclient.utils;

public class Constant {
    public final static String apiScheme = "http";
    public final static String apiServer = "192.168.1.100";
    public final static int apiPort = 8080;
    public final static String apiUrl = String.format("%s://%s:%d",apiScheme,apiServer,apiPort);
    public final static String apiRegister = String.format("%s%s",apiUrl,"/user/register");
    public final static String apiLogin = String.format("%s%s",apiUrl,"/user/login");

    public final static String apiBodyData = "data";
    public final static String apiBody_userid = "userId";
    public final static String apiBody_username = "username";
    public final static String apiBody_password = "password";
    public final static String apiBody_role = "role";
    public final static String apiBody_createtime = "createTime";
    public final static String apiBody_updatetime= "updateTime";
    public final static String apiBody_telephone= "telephone";
    public final static String apiBody_statuscode = "status";
    public final static String apiBody_message = "message";
    public final static int apiBody_errorcode = 500;


    public final static String sharedP_USER_INFO = "userinfo";
    public final static String sharedP_USER_NAME = "username";
    public final static String sharedP_USER_PWD = "userpwd";
    public final static String sharedP_USER_ID = "userid";
    public final static String sharedP_USER_ROLE = "userrole";
    public final static String sharedP_USER_CREATE_TIME = "createtime";
    public final static String sharedP_USER_UPDATE_TIME = "updatetime";
    public final static String sharedP_USER_TELPHONE = "telphone";

    public final static String api_username = "username";
    public final static String api_password = "password";
    public final static String api_telephone = "telephone";
}
