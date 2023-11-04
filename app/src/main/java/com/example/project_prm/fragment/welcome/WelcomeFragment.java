package com.example.project_prm.fragment.welcome;

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

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.signup.SignUpFragment;

public class WelcomeFragment extends Fragment {

    private Button Login;
    private Button Sign_Up;

    public WelcomeFragment() {
        // Required empty public constructor
    }
    private void initView() {
        ((MainActivity) getActivity()).showTitleBar(false, null);
        Login = getView().findViewById(R.id.btnLoginWelcome);
        Sign_Up = getView().findViewById(R.id.btnSignUpWelcome);
    }

    private void initAction(){
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFragment(LoginFragment.newInstance("",""), "LoginFragment");

            }
        });

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFragment(SignUpFragment.newInstance("" , "", "", ""), "SignUpFragment");

            }
        });
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
    }

    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    private void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    public static final String TAG = "WelcomeFragment";
}