package com.example.project_prm.fragment.setting.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.setting.SettingCallback;
import com.example.project_prm.fragment.setting.SettingController;
import com.example.project_prm.fragment.setting.DialogChangeUserImage.DialogChangeUserImageFragment;
import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;


public class SettingProfileFragment extends Fragment {

    private static final String ADDRESS = "ADDRESS";
    private static final String NEW_PASSWORD = "NEW_PASSWORD";
    private Button btnSave;
    private EditText edtOldPassWord;
    private boolean passVisible;
    private EditText edtNewPassWord;
    private EditText edtConfPassWord;
    private ImageView imgUserImage;
    private EditText edtAddress;
    private TextView userName;
    private TextView userEmail;
    private SettingProfileCallback settingProfileCallback;
    private SettingProfileController settingProfileController;
    private SettingController settingController;

    private void initView() {
        btnSave = getView().findViewById(R.id.btnSaveData);
        edtOldPassWord = getView().findViewById(R.id.edtOldPassword);
        edtNewPassWord = getView().findViewById(R.id.edtNewPassword);
        edtConfPassWord = getView().findViewById(R.id.edtconfPassword);
        imgUserImage = getView().findViewById(R.id.userImage);
        edtAddress = getView().findViewById(R.id.edtAddress);
        userName = getView().findViewById(R.id.txtSettingName);
        userEmail = getView().findViewById(R.id.txtSettingEmail);
    }

    private void initAction(){
        imgUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DialogChangeUserImageFragment changeUserImageFragment = new DialogChangeUserImageFragment();
                changeUserImageFragment.setOnImageCaptureListener((MainActivity) getActivity());
                changeUserImageFragment.show(fragmentManager, DialogChangeUserImageFragment.TAG);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
                String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                String username = sharedPreferencesUtil.getData(SharedPreferencesKey.USERNAME);
                String userEmail = sharedPreferencesUtil.getData(SharedPreferencesKey.EMAIL);
                String currentPassword = sharedPreferencesUtil.getData(SharedPreferencesKey.PASSWORD);
                String address = edtAddress.getText().toString();
                String OldPassword = edtOldPassWord.getText().toString();
                String NewPassword = edtNewPassWord.getText().toString();
                String confirmPassword = edtConfPassWord.getText().toString();
                if((OldPassword.isEmpty() && NewPassword.isEmpty() && confirmPassword.isEmpty())
                        ||(!OldPassword.isEmpty() && !NewPassword.isEmpty()
                        && !confirmPassword.isEmpty() && NewPassword.equals(confirmPassword)
                        && OldPassword.equals(currentPassword) && NewPassword.length()>=6
                        && !containsSpace(NewPassword))) {
                    settingProfileController.updateData(uid, username, userEmail, address, NewPassword);
                }else {
                    Toast.makeText(requireContext(), R.string.incorrect_password, Toast.LENGTH_LONG).show();
                }
            }
        });

        edtOldPassWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edtOldPassWord.getRight()-edtOldPassWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtOldPassWord.getSelectionEnd();
                        if(passVisible){
                            edtOldPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            edtOldPassWord.setTransformationMethod(new PasswordTransformationMethod());
                            passVisible = false;
                        }else{
                            edtOldPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            edtOldPassWord.setTransformationMethod(null);
                            passVisible = true;
                        }
                        edtOldPassWord.setSelection(selection);
                        return true ;
                    }
                }
                return false;
            }
        });

        edtConfPassWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edtConfPassWord.getRight()-edtConfPassWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtConfPassWord.getSelectionEnd();
                        if(passVisible){
                            edtConfPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            edtConfPassWord.setTransformationMethod(new PasswordTransformationMethod());
                            passVisible = false;
                        }else{
                            edtConfPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            edtConfPassWord.setTransformationMethod(null);
                            passVisible = true;
                        }
                        edtConfPassWord.setSelection(selection);
                        return true ;
                    }
                }
                return false;
            }
        });
        edtNewPassWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2 ;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if(event.getRawX() >= edtNewPassWord.getRight()-edtNewPassWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = edtNewPassWord.getSelectionEnd();
                        if(passVisible){
                            edtNewPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_off_24,0);
                            edtNewPassWord.setTransformationMethod(new PasswordTransformationMethod());
                            passVisible = false;
                        }else{
                            edtNewPassWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.baseline_visibility_24,0);
                            edtNewPassWord.setTransformationMethod(null);
                            passVisible = true;
                        }
                        edtNewPassWord.setSelection(selection);
                        return true ;
                    }
                }
                return false;
            }
        });
    }

    private void initObserver() {
        settingProfileCallback = new SettingProfileCallback() {
            @Override
            public void onUpdateResult(boolean result, String message) {
                if(result) {
                    SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getContext());
                    String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                    sharedPreferencesUtil.deleteData(SharedPreferencesKey.USERID);
                    sharedPreferencesUtil.deleteData(SharedPreferencesKey.EMAIL);
                    sharedPreferencesUtil.deleteData(SharedPreferencesKey.USERNAME);
                    sharedPreferencesUtil.deleteData(SharedPreferencesKey.PASSWORD);
                    settingController.logOut(uid);
                    replaceFragment(LoginFragment.newInstance("",""), LoginFragment.TAG);
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                }
            }
            public void onGetUserResult(boolean result, String message, Info u) {
                getUserInfo(u);
            }

            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }
        };
        settingProfileController = new SettingProfileController(settingProfileCallback);
        settingController = new SettingController();
        getUserProfileByID();
    }

    private void getUserInfo(Info u) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String email = sharedPreferencesUtil.getData(SharedPreferencesKey.EMAIL);
        String name = sharedPreferencesUtil.getData(SharedPreferencesKey.USERNAME);
        userEmail.setText(email);
        userName.setText(name);
        //edtOldPassWord.setText(u.getPassword());
        edtAddress.setText(u.getAddress());
    }

    private void getUserProfileByID() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        settingProfileController.getUserProfile(uid);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
        initObserver();
    }
    public SettingProfileFragment() {
    }

    public static SettingProfileFragment newInstance(String address,String new_password) {
        SettingProfileFragment fragment = new SettingProfileFragment();
        Bundle args = new Bundle();
        args.putString(ADDRESS, address);
        args.putString(NEW_PASSWORD, new_password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO
        }
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
        return inflater.inflate(R.layout.fragment_setting_profile, container, false);
    }
    private boolean containsSpace(String text) {
        return text.contains(" ");
    }
    public static final String TAG = "SettingProfileFragment";
}