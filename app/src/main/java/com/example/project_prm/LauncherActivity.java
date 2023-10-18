package com.example.project_prm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initAction();
    }

    private void initAction() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainAcitivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAcitivityIntent);
                finish();
            }
        }, 2000);
    }
}