package com.example.project_prm.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.chats.ChatsFragment;
import com.example.project_prm.fragment.friends.FriendsFragment;
import com.example.project_prm.fragment.notification.NotificationFragment;
import com.example.project_prm.fragment.setting.SettingFragment;
import com.example.project_prm.fragment.userprofile.UserProfileFragment;
import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Friend;
import com.example.project_prm.model.Notification;
import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    private TextView tvBagde;

    private HomeController homeController;

    private ArrayList<Notification> currentNotifications;

    private ArrayList<Friend> currentFriends;

    private HomeCallBack homeCallBack;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
        bottomNavigationView = getView().findViewById(R.id.bnvNavigation);
        initTvBadge();
        ((MainActivity) getActivity()).showTitleBar(true, "Chats");
        replaceFragment(ChatsFragment.newInstance(), ChatsFragment.TAG);
    }

    private void initTvBadge() {
        BottomNavigationItemView itemView = bottomNavigationView.findViewById(R.id.notification);
        View badge = LayoutInflater.from(getContext()).inflate(R.layout.icon_badge_number_layout, itemView, true);
        tvBagde = badge.findViewById(R.id.tvBadge);
    }

    private void initAction() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.chats && getActivity().getSupportFragmentManager().findFragmentByTag(ChatsFragment.TAG) == null) {
                    ((MainActivity) getActivity()).showTitleBar(true, "Chats");
                    replaceFragment(ChatsFragment.newInstance(), ChatsFragment.TAG); // add chat fragment
                } else if (item.getItemId() == R.id.setting && getActivity().getSupportFragmentManager().findFragmentByTag(SettingFragment.TAG) == null) {
                    ((MainActivity) getActivity()).showTitleBar(true, "Setting");
                    replaceFragment(SettingFragment.newInstance(), SettingFragment.TAG); // add setting fragment
                } else if (item.getItemId() == R.id.notification && getActivity().getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG) == null) {
                    ((MainActivity) getActivity()).showTitleBar(true, "Notifications");
                    replaceFragment(NotificationFragment.newInstance(currentNotifications), NotificationFragment.TAG); // add notification fragment
                } else if (item.getItemId() == R.id.friend && getActivity().getSupportFragmentManager().findFragmentByTag(FriendsFragment.TAG) == null ){
                    ((MainActivity) getActivity()).showTitleBar(true, "Friends");
                    replaceFragment(FriendsFragment.newInstance(currentFriends), FriendsFragment.TAG); // add friend fragment
                }
                return true;
            }
        });
    }

    private void initObserver() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.findFragmentByTag(ChatsFragment.TAG) != null) {
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).showTitleBar(true, "Chats");
                        bottomNavigationView.setSelectedItemId(R.id.chats);
                    }
                } else if (fragmentManager.findFragmentByTag(FriendsFragment.TAG) != null) {
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).showTitleBar(true, "Friends");
                        bottomNavigationView.setSelectedItemId(R.id.friend);
                    }
                }
            }
        });

        homeCallBack = new HomeCallBack() {
            @Override
            public void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications) {
                currentNotifications = new ArrayList<>();
                currentNotifications.clear();
                currentNotifications.addAll(notifications);
                updateNotificationBadge(currentNotifications.size());
            }

            @Override
            public void onUserFriendResult(boolean result, String message, ArrayList<Friend> friends) {
                currentFriends = new ArrayList<>();
                currentFriends.clear();
                currentFriends.addAll(friends);
            }

            @Override
            public void onChatUpdate(boolean result, String message, ArrayList<Chats> Chats) {

            }

            @Override
            public void onUserProfile(boolean result, String message, Info u) {

            }

            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }
        };
        homeController = new HomeController(homeCallBack);
        getNotificationFromUserId();
        getFriendsFromUserId();
    }

    private void getNotificationFromUserId() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        homeController.getNotification(uid);
    }

    private void getFriendsFromUserId() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        homeController.getFriends(uid);
    }

    private void updateNotificationBadge(int size) {
        if (size == 0) {
            tvBagde.setVisibility(View.INVISIBLE);
        } else if (size > 9) {
            tvBagde.setVisibility(View.VISIBLE);
            tvBagde.setText("9+");
        } else {
            tvBagde.setVisibility(View.VISIBLE);
            tvBagde.setText(String.valueOf(size));
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

    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.homeFragmentContainer, fragment, tag);
        fragmentTransaction.commit();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    public static final String TAG = "HomeFragment";
}