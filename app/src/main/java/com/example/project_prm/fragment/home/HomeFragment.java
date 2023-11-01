package com.example.project_prm.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_prm.R;
import com.example.project_prm.fragment.chats.ChatsFragment;
import com.example.project_prm.fragment.friends.FriendsFragment;
import com.example.project_prm.fragment.notification.NotificationFragment;
import com.example.project_prm.fragment.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
        initObserver();
    }

    private void initView() {
        bottomNavigationView = getView().findViewById(R.id.bnvNavigation);
        replaceFragment(ChatsFragment.newInstance("",""), "ChatsFragment");
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.chats && getActivity().getSupportFragmentManager().findFragmentByTag(ChatsFragment.TAG) == null) {
                    replaceFragment(ChatsFragment.newInstance("", ""), "ChatsFragment"); // add chat fragment
                } else if (item.getItemId() == R.id.setting && getActivity().getSupportFragmentManager().findFragmentByTag(SettingFragment.TAG) == null) {
                    replaceFragment(SettingFragment.newInstance("", ""), "SettingFragment"); // add setting fragment
                } else if (item.getItemId() == R.id.notification && getActivity().getSupportFragmentManager().findFragmentByTag(NotificationFragment.TAG) == null) {
                    replaceFragment(NotificationFragment.newInstance("", ""), "NotificationFragment"); // add notification fragment
                } else if (item.getItemId() == R.id.friend && getActivity().getSupportFragmentManager().findFragmentByTag(FriendsFragment.TAG) == null ){
                    replaceFragment(FriendsFragment.newInstance("", ""), "FriendsFragment"); // add friend fragment
                }
                return true;
            }
        });
    }

    private void initAction() {

    }

    private void initObserver() {

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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG = "HomeFragment";
}