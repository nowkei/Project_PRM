package com.example.project_prm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.Manifest;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.setting.DialogChangeUserImage.DialogChangeUserImageFragment;
import com.example.project_prm.fragment.welcome.WelcomeFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity implements DialogChangeUserImageFragment.OnImageCaptureListener {

    private static final int CAMERA_REQUEST_CODE = 100;
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

    @Override
    public void onImageCaptureRequested() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}