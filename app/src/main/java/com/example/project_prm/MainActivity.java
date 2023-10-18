package com.example.project_prm;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.welcome.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
    }

    private void initView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out)
                .setReorderingAllowed(true);

        if (!isInit) {
            fragmentTransaction.add(R.id.fragmentContainer, WelcomeFragment.newInstance(), LoginFragment.TAG)
                    .commit();
            isInit = true;
        }
    }

    @Override
    protected void onDestroy() {
        isInit = false;
        super.onDestroy();
    }

    private void initAction() {

    }

}