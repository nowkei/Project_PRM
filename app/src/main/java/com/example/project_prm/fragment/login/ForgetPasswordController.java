package com.example.project_prm.fragment.login;

import androidx.annotation.NonNull;

import com.example.project_prm.fragment.setting.profile.SettingProfileCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordController {
    private ForgetPasswordCallBack forgetPasswordCallBack;
    private FirebaseAuth firebaseAuth;
    public ForgetPasswordController(ForgetPasswordCallBack forgetPasswordCallBack) {
        this.forgetPasswordCallBack = forgetPasswordCallBack;
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void sendEmail(String sendEmail) {
        forgetPasswordCallBack.onLoading(true);
        firebaseAuth.sendPasswordResetEmail(sendEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    forgetPasswordCallBack.onSendEmailResult(true, "Check Email for get new password");
                } else {
                    forgetPasswordCallBack.onSendEmailResult(false, "Invalid Email");
                }
                forgetPasswordCallBack.onLoading(false);
            }
        });
    }
}
