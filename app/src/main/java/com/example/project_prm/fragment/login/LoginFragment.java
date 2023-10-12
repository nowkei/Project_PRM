package com.example.project_prm.fragment.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_prm.R;


public class LoginFragment extends Fragment {

    private EditText edtPassword;
    private EditText edtUsername;
    private Button btnLogin;
    private LoginController loginController;

    private LoginCallback loginCallback;

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
        initView();
        initAction();
        initObserver();
    }

    private void initView() {
        btnLogin = getView().findViewById(R.id.btnLogin);
        edtUsername = getView().findViewById(R.id.edtUsername);
        edtPassword = getView().findViewById(R.id.edtPassword);
    }

    private void initAction() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                loginController.login(username, password);
            }
        });
    }
    private void initObserver() {
        loginCallback = new LoginCallback() {
            @Override
            public void onLoginResult(boolean result, String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSomethingResult() {
                Toast.makeText(requireContext(), "Hehe", Toast.LENGTH_LONG).show();
            }
        };
        loginController = new LoginController(loginCallback);
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public static final String TAG = "LoginFragment";
    private static final String PASSWORD = "LoginPassword";
    private static final String USERNAME = "LoginUsername";
}