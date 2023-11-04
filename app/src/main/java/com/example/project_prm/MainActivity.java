package com.example.project_prm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.welcome.WelcomeFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    private CardView cvTabTitle;

    private TextView tvTabTitle;

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
        cvTabTitle = findViewById(R.id.cvTabTitle);
        tvTabTitle = findViewById(R.id.tvTabTitle);
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

    public void showLoading(boolean isLoading) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLoading) {
            DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
            loadingDialog.show(fragmentManager, DialogLoadingFragment.TAG);
        } else {
            DialogLoadingFragment dialogLoadingFragment = (DialogLoadingFragment) fragmentManager.findFragmentByTag(DialogLoadingFragment.TAG);
            if (dialogLoadingFragment != null)
                dialogLoadingFragment.dismiss();
        }
    }

    public void showTitleBar(boolean isShow, String content) {
        if (!isShow) {
            cvTabTitle.setVisibility(View.GONE);
        } else {
            cvTabTitle.setVisibility(View.VISIBLE);
            tvTabTitle.setText(content);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}