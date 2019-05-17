package com.example.expressclient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.example.expressclient.bean.User;
import com.example.expressclient.utils.NetTools;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginSigninActivity extends AppCompatActivity  {


    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private EditText mTelephone;
    private CheckBox msigninBox;
    private boolean isSignin;
    private View msignInView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signin);

        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                // 键盘回车 时调用登陆
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button signButton = findViewById(R.id.btn_login);
        signButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        msignInView = findViewById(R.id.signininput);
        msigninBox = findViewById(R.id.ckb_is_signin);
        mTelephone = findViewById(R.id.telephone);

        msigninBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msignInView.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                isSignin = isChecked;
                if ( isChecked ) {
                    signButton.setText(getString(R.string.action_sign_in));
                } else {
                    signButton.setText(getString(R.string.action_login));
                }

            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // 获取输入的字符串
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String telephone = mTelephone.getText().toString();

        boolean cancel = false; // 有没有出错
        View focusView = null;  // 出错的框

        if ( isSignin ){
            // 注册的检查
            if ( TextUtils.isEmpty(telephone) ){
                focusView = mTelephone;
                mTelephone.setError(getString(R.string.error_field_required));
                cancel = true;
            }
            if ( !isPasswordValid(password) ){
                focusView = mPasswordView;
                mPasswordView.setError(getString(R.string.error_invalid_password));
                cancel = true;
            }
            if ( TextUtils.isEmpty(username) ){
                focusView = mUsernameView;
                mUsernameView.setError(getString(R.string.error_field_required));
                cancel = true;
            }
        }else{
            // 登录的检查
            if ( TextUtils.isEmpty(password) ){
                focusView = mPasswordView;
                ((EditText) focusView).setError(getString(R.string.error_field_required));
                cancel = true;
            }
            if ( TextUtils.isEmpty(username) ){
                focusView = mUsernameView;
                ((EditText) focusView).setError(getString(R.string.error_field_required));
                cancel = true;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password,telephone);
            mAuthTask.execute();
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }



    public class UserLoginTask extends AsyncTask<Integer, Void, Boolean> {

        private final String username;
        private final String mPassword;
        private final String telephone;

        UserLoginTask(String email, String password,String telephone) {
            username = email;
            mPassword = password;
            this.telephone = telephone;
        }

        @Override
        protected Boolean doInBackground(Integer... params) {

            try {
                if ( telephone == null ){
                    // 登录操作
                    User uu = new User(this.username,this.mPassword,null);
                    uu = NetTools.loginUser(uu);
                    NetTools.cacheUser(uu);

                } else {
                    User uu = new User(this.username,this.mPassword,this.telephone);
                    NetTools.registerUser(uu);
                }
            } catch (Exception e) {
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success && telephone==null) {
                finish();
            } else if ( telephone != null ){
                mPasswordView.clearComposingText();
                mTelephone.clearComposingText();
                msigninBox.setChecked(false);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }



        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

