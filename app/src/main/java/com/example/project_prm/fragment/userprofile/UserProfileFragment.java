package com.example.project_prm.fragment.userprofile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

public class UserProfileFragment extends Fragment {

    private Button btnChatOrAddFriend;

    private ImageView imvAvatar;

    private ImageView imvBackground;

    private TextView tvUsername;

    private TextView tvEmail;

    private UserProfileController userProfileController;

    private TextView tvAlreadyAddFriend;

    public UserProfileFragment() {}
    public static UserProfileFragment newInstance(Info info) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(OTHER_INFO, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
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
        btnChatOrAddFriend = getView().findViewById(R.id.btnChatOrAddFriend);
        imvBackground = getView().findViewById(R.id.imvOtherProfileBackground);
        imvAvatar = getView().findViewById(R.id.imvOtherUserImage);
        tvUsername = getView().findViewById(R.id.tvUserProfileUserName);
        tvEmail = getView().findViewById(R.id.tvUserProfileEmail);
        tvAlreadyAddFriend = getView().findViewById(R.id.tvAlreadyAddFriend);

        if (getArguments() != null) {
            Info info = getArguments().getParcelable(OTHER_INFO);
            tvEmail.setText(info.getEmail());
            tvUsername.setText(info.getUsername());
            Glide.with(getContext()).load(info.getAvatar()).error(R.drawable.userimage).circleCrop().into(imvAvatar);
            Glide.with(getContext()).load(info.getAvatar()).error(R.drawable.userimage).fitCenter().into(imvBackground);
            if (info.isAddFriend()) {
               tvAlreadyAddFriend.setVisibility(View.VISIBLE);
               btnChatOrAddFriend.setVisibility(View.GONE);
            } else {
                btnChatOrAddFriend.setText(getString(R.string.add_friend));
                btnChatOrAddFriend.setVisibility(View.VISIBLE);
                tvAlreadyAddFriend.setVisibility(View.GONE);
            }
        }
    }

    private void initAction() {
        btnChatOrAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info info = getArguments().getParcelable(OTHER_INFO);
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
                String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                String userName = sharedPreferencesUtil.getData(SharedPreferencesKey.USERNAME);
                userProfileController.sendFriendRequest(uid, userName, "", info.getUid());
            }
        });
    }

    private void initObserver() {
        userProfileController = new UserProfileController(new UserProfileCallBack() {
            @Override
            public void onAddFriend(boolean status, String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }

            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    public static final String OTHER_INFO = "OTHER_INFO";

    public static final String TAG = "UserProfileFragment";
}