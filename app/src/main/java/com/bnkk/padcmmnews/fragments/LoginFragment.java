package com.bnkk.padcmmnews.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bnkk.padcmmnews.R;
import com.bnkk.padcmmnews.delegates.LoginRegisterDelegate;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by E5-575G on 11/30/2017.
 */

public class LoginFragment extends Fragment {

    private LoginRegisterDelegate mLoginRegisterDelegate;

    public static LoginFragment newInstance() {
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginRegisterDelegate = (LoginRegisterDelegate) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, loginView);

        return loginView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoginRegisterDelegate.setScreenTitle("Login");
    }

    @OnClick(R.id.btn_to_register)
    public void onTapToRegister(View view) {
        mLoginRegisterDelegate.onTapToRegister();
    }
}
