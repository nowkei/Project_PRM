package com.example.project_prm.fragment.signup;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_prm.R;
import com.example.project_prm.component.DialogLoadingFragment;

public class SignUpFragment extends Fragment {
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPassword;
    private boolean passVisible;
    private Button btnSignup;
    private SignUpController signUpController;
    private SignUpCallBack signUpCallBack;
    public SignUpFragment() {}

    public static SignUpFragment newInstance(String username, String email, String password) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(EMAIL, email);
        args.putString(PASSWORD, password);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
        initObserver();
    }
    private void initView() {
        btnSignup = getView().findViewById(R.id.btnSignUp);
        edtUsername = getView().findViewById(R.id.edtUsername);
        edtEmail = getView().findViewById(R.id.edtEmail);
        edtPassword = getView().findViewById(R.id.edtPassword);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAction() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                signUpController.signUp(username, email, password);
            }
        });
        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX()>= edtPassword.getRight()-edtPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtPassword.getSelectionEnd();
                        if(passVisible){
                            edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                            passVisible = false;
                        }else{
                            edtPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            edtPassword.setTransformationMethod(null);
                            passVisible = true;
                        }
                        edtPassword.setSelection(selection);
                        return true ;
                    }
                }
                return false;
            }
        });
    }
    private void initObserver() {
        signUpCallBack = new SignUpCallBack() {
            @Override
            public void onSignUpResult(boolean result, String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoading(boolean isLoading) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (isLoading) {
                    DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
                    loadingDialog.show(fragmentManager, DialogLoadingFragment.TAG);
                } else {
                    DialogLoadingFragment dialogLoadingFragment = (DialogLoadingFragment) fragmentManager.findFragmentByTag(DialogLoadingFragment.TAG);
                    dialogLoadingFragment.dismiss();
                }
            }

        };
        signUpController = new SignUpController(signUpCallBack);
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    public static final String TAG = "SignUpFragment";
    private static final String USERNAME = "SignUpUsername";
    private static final String EMAIL = "SignUpEmail";
    private static final String PASSWORD ="SignUpPassword";

}