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

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.setting.SettingController;
import com.example.project_prm.fragment.setting.profile.SettingProfileCallback;
import com.example.project_prm.fragment.setting.profile.SettingProfileController;
import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;


public class ForgetPasswordFragment extends Fragment {

    private EditText email;
    private Button confirmEmail;
    private TextView back;
    private ForgetPasswordController forgetPasswordController;
    private ForgetPasswordCallBack forgetPasswordCallBack;
    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    private void initView() {
        email = getView().findViewById(R.id.enterEmail);
        confirmEmail = getView().findViewById(R.id.btnResetPassword);
        back = getView().findViewById(R.id.Back);
    }

    private void initAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
//                replaceFragment(LoginFragment.newInstance("", ""), LoginFragment.TAG);
            }
        });
        confirmEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendEmail = email.getText().toString();
                forgetPasswordController.sendEmail(sendEmail);
            }
        });
    }
    public static ForgetPasswordFragment newInstance() {
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    private void initObserver() {
        forgetPasswordCallBack = new ForgetPasswordCallBack() {
            @Override
            public void onSendEmailResult(boolean result, String message) {
                if(result) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }
        };
        forgetPasswordController = new ForgetPasswordController(forgetPasswordCallBack);

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initView();
        initAction();
        initObserver();
    }

    public static final String TAG = "ForgetPasswordFragment";
}