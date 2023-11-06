package com.example.project_prm.fragment.setting;

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
import android.widget.TextView;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.friends.FriendsFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.setting.profile.SettingProfileFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    private Button btnLogout;
    private Button btnSetting;
    private Button btnShowFriend;
    private  SettingController settingController;

    private TextView username;
    private TextView userEmail;
    private void initView() {
        btnShowFriend = getView().findViewById(R.id.btnShowFriend);
        btnLogout = getView().findViewById(R.id.btnSaveData);
        btnSetting = getView().findViewById(R.id.btnSettingProfile);
        username = getView().findViewById(R.id.txtName);
        userEmail = getView().findViewById(R.id.txtEmail);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
        String userName = sharedPreferencesUtil.getData(SharedPreferencesKey.USERNAME);
        String email = sharedPreferencesUtil.getData(SharedPreferencesKey.EMAIL);

        username.setText(userName);
        userEmail.setText(email);
    }

    private void initAction(){

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
                String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                sharedPreferencesUtil.deleteData(SharedPreferencesKey.USERID);
                sharedPreferencesUtil.deleteData(SharedPreferencesKey.EMAIL);
                sharedPreferencesUtil.deleteData(SharedPreferencesKey.USERNAME);
                settingController.logOut(uid);
                replaceFragment(LoginFragment.newInstance("",""), LoginFragment.TAG);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFragmentSetting(SettingProfileFragment.newInstance("", ""),"SettingProfileFragment");
            }
        });
    }

    private void initObserver() {
        settingController = new SettingController();
    }
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
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
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

    private void addFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.add(R.id.homeFragmentContainer, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }
    private void addFragmentSetting(Fragment fragment, String tag) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    public static final String TAG = "SettingFragment";
}