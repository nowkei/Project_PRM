package com.example.project_prm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.welcome.WelcomeFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
    }

    private void initAction() {

    }

    private void initView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out)
                .setReorderingAllowed(true);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        if (sharedPreferencesUtil.getData(SharedPreferencesKey.USERID) != null && !sharedPreferencesUtil.getData(SharedPreferencesKey.USERID).isEmpty()) {
            fragmentTransaction.add(R.id.fragmentContainer, HomeFragment.newInstance(), HomeFragment.TAG)
                    .commit();
        } else {
            fragmentTransaction.add(R.id.fragmentContainer, WelcomeFragment.newInstance(), WelcomeFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}