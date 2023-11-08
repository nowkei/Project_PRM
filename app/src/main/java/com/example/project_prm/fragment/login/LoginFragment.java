package com.example.project_prm.fragment.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.signup.SignUpFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;


public class LoginFragment extends Fragment {

    private EditText edtPassword;
    private boolean passVisible;
    private EditText edtEmail;
    private Button btnLogin;
    private TextView tvSignUp;
    private LoginController loginController;
    private LoginCallback loginCallback;
    private TextView resetPassword;
    public LoginFragment() {}

    public static LoginFragment newInstance(String username, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.post(new Runnable() {
            @Override
            public void run() {
                initView();
                initAction();
                initObserver();
            }
        });
    }

    private void initView() {
        ((MainActivity) getActivity()).showTitleBar(false, null);
        btnLogin = getView().findViewById(R.id.btnLogin);
        edtEmail   = getView().findViewById(R.id.edtUsername);
        edtPassword = getView().findViewById(R.id.edtPassword);
        tvSignUp = getView().findViewById(R.id.signUp);
        resetPassword = getView().findViewById(R.id.changePass);

        if (!getArguments().getString(USERNAME).isEmpty() || getArguments().getString(USERNAME) != null) {
            edtEmail.setText(getArguments().getString(USERNAME));
            if (!getArguments().getString(PASSWORD).isEmpty() || getArguments().getString(PASSWORD) != null) {
                edtPassword.setText(getArguments().getString(PASSWORD));
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAction() {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(ForgetPasswordFragment.newInstance(), ForgetPasswordFragment.TAG);
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragment(SignUpFragment.newInstance("","", "", ""), SignUpFragment.TAG);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(requireContext(), "Please enter your username and password", Toast.LENGTH_LONG).show();
                } else if (containsSpace(email) || containsSpace(password)) {
                    Toast.makeText(requireContext(), "Cannot contain spaces", Toast.LENGTH_LONG).show();
                }
                else{
                    loginController.login(email, password);
                }
            }
        });

        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edtPassword.getRight()-edtPassword.getCompoundDrawables()[Right].getBounds().width()){
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
        loginCallback = new LoginCallback() {
            @Override
            public void onLoginResult(boolean result, String message, String username, String email, String userID, String pass, String avatar) {
                if (result) {
                    SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());

                    sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.USERNAME, username);
                    sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.EMAIL, email);
                    sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.USERID, userID);
                    sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.PASSWORD, pass);
                    sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.AVATAR, avatar);

                    replaceFragment(HomeFragment.newInstance(), HomeFragment.TAG);
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }

        };
        loginController = new LoginController(loginCallback);
    }

    private void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                        R.anim.anim_slide_in,
                        R.anim.anim_fade_out,
                        R.anim.anim_fade_in,
                        R.anim.anim_slide_out);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out);
        fragmentTransaction.setReorderingAllowed(true);
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public static final String TAG = "LoginFragment";
    private static final String PASSWORD = "LoginPassword";
    private static final String USERNAME = "LoginUsername";
    private boolean containsSpace(String text) {
        return text.contains(" ");
    }
}