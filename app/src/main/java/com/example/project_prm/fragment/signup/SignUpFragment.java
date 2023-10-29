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
import android.widget.TextView;
import android.widget.Toast;


import com.example.project_prm.R;
import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.fragment.login.LoginFragment;


public class SignUpFragment extends Fragment {
    private EditText edtUsername;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtCfPassword;
    private boolean passVisible;
    private Button btnSignup;
    private TextView tvLogin;
    private SignUpController signUpController;
    private SignUpCallBack signUpCallBack;

    public SignUpFragment() {
    }

    public static SignUpFragment newInstance(String username, String email, String password, String cfPassword) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(USERNAME, username);
        args.putString(EMAIL, email);
        args.putString(PASSWORD, password);
        args.putString(CONFIRM_PASSWORD, cfPassword);
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
        edtCfPassword = getView().findViewById(R.id.edtCfPassword);
        tvLogin = getView().findViewById(R.id.tvLogin);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initAction() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(LoginFragment.newInstance("", ""), SignUpFragment.TAG);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String cfPassword = edtCfPassword.getText().toString();
                if (username.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.empty_username, Toast.LENGTH_LONG).show();
                }
                if (email.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.empty_email, Toast.LENGTH_LONG).show();
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(requireContext(), R.string.validate_email, Toast.LENGTH_LONG).show();
                }

                if (password.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.empty_password, Toast.LENGTH_LONG).show();

                } else if (password.length() < 6) {
                    Toast.makeText(requireContext(), R.string.validate_password, Toast.LENGTH_LONG).show();
                }

                if (cfPassword.isEmpty()) {
                    Toast.makeText(requireContext(), R.string.empty_confirmPassword, Toast.LENGTH_LONG).show();
                }
                if (password.equals(cfPassword)) {
                    signUpController.signUp(username, email, password, cfPassword);
                } else {
                    Toast.makeText(requireContext(), R.string.match_password, Toast.LENGTH_LONG).show();
                }
            }
        });
        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handlePasswordVisibility(edtPassword, event);
                return false;
            }
        });

        edtCfPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handlePasswordVisibility(edtCfPassword, event);
                return false;
            }
        });
    }

    private void handlePasswordVisibility(EditText editText, MotionEvent event) {
        final int Right = 2;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= editText.getRight() - editText.getCompoundDrawables()[Right].getBounds().width()) {
                int selection = editText.getSelectionEnd();
                if (passVisible) {
                    editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
                    editText.setTransformationMethod(new PasswordTransformationMethod());
                    passVisible = false;
                } else {
                    editText.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
                    editText.setTransformationMethod(null);
                    passVisible = true;
                }
                editText.setSelection(selection);
            }
        }
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
    private static final String PASSWORD = "SignUpPassword";
    private static final String CONFIRM_PASSWORD = "SignUpCfPassword";

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        return email.matches(emailPattern);
    }
}