package com.example.expressclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.expressclient.bean.User;
import com.example.expressclient.utils.NetTools;

import org.w3c.dom.Text;


public class nav_userCenter extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListenerNavUserCenter mListener;
    private Button btn;
    private boolean btn_logout = false;
    private TextView tv_username;
    public nav_userCenter() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_user_center, container, false);
        btn = view.findViewById(R.id.btn_loginregister);
        btn.setOnClickListener(this);
        tv_username = view.findViewById(R.id.tv_username);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListenerNavUserCenter) {
            mListener = (OnFragmentInteractionListenerNavUserCenter) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListenerNavUserCenter");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        User uu = NetTools.loadCachedUser();
        if ( uu != null ){ // 用户登陆了
            btn.setText(getString(R.string.logout));
            btn_logout = true;
            tv_username.setText(uu.getUsername());
            tv_username.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListenerNavUserCenter {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {
        if ( v.getId() == R.id.btn_loginregister && !btn_logout){
            startActivity(new Intent(getContext(),LoginSigninActivity.class));
        }
        if ( v.getId() == R.id.btn_loginregister && btn_logout){
            // 登出
            btn_logout = false;
            NetTools.cleanUser();
            btn.setText(R.string.loginsignup);
            tv_username.setVisibility(View.GONE);
        }
    }
}
