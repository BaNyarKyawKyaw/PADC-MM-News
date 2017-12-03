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

/**
 * Created by E5-575G on 11/30/2017.
 */

public class RegisterFragment extends BaseFragment {

    private LoginRegisterDelegate mLoginRegisterDelegate;

    public static RegisterFragment newInstance() {
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLoginRegisterDelegate = (LoginRegisterDelegate) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View registerView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, registerView);
        return registerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoginRegisterDelegate.setScreenTitle("Register");
    }
}
