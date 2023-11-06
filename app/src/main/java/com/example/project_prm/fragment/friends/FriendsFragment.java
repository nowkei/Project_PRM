package com.example.project_prm.fragment.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project_prm.R;
import com.example.project_prm.fragment.findotheruser.FindOtherUsersFragment;
import com.example.project_prm.fragment.friends.adapter.FriendItemAdapter;
import com.example.project_prm.model.Friend;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {

    private RecyclerView rcvFriends;

    private FriendItemAdapter adapter;

    private FriendsCallBack friendsCallBack;

    private FriendsController friendsController;

    private ArrayList<Friend> currentFriends;

    private Button btnFindOtherUser;

    public FriendsFragment() {
        // Required empty public constructor
    }

    public static FriendsFragment newInstance(ArrayList<Friend> friends) {
        FriendsFragment fragment = new FriendsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FRIENDS, friends);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initRcvFriends();
        btnFindOtherUser = getView().findViewById(R.id.btnFindFriend);
    }

    private void initRcvFriends() {
        rcvFriends = getView().findViewById(R.id.rcvFriendList);
        rcvFriends.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false ));
        if (getArguments() != null) {
            currentFriends = getArguments().getParcelableArrayList(FRIENDS);
            adapter = new FriendItemAdapter(currentFriends, requireContext());
        } else {
            adapter = new FriendItemAdapter(new ArrayList<>(), requireContext());
        }
        rcvFriends.setAdapter(adapter);
    }

    private void initAction() {
        btnFindOtherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmentFromFragmentContainerHost(FindOtherUsersFragment.newInstance(), FindOtherUsersFragment.TAG);
            }
        });
    }

    private void addFragmentFromFragmentContainerHost(Fragment fragment, String tag) {
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

    private void initObserver() {
        friendsCallBack = new FriendsCallBack() {
            @Override
            public void onFriendResult(boolean status, String result, ArrayList<Friend> u) {
                adapter.changeDataSet(u);
            }

            @Override
            public void onLoading(boolean isLoading) {

            }

            @Override
            public void onUserLoginStatusChange(boolean status, String message, String uid, boolean updatedStatus) {
                ArrayList<Friend> updateFriend = new ArrayList<>();
                for (Friend friend : currentFriends) {
                    if (friend.getUser().getUid().equals(uid)) {
                        friend.setOnline( updatedStatus );
                    }
                    updateFriend.add(friend);
                }
                adapter.changeDataSet(updateFriend);
            }
        };
        friendsController = new FriendsController(friendsCallBack);
        getFriendFromId();
        friendsController.checkFriendLogIn(getArguments().getParcelableArrayList(FRIENDS));
    }

    private void getFriendFromId() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        friendsController.getFriends(uid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    private static final String FRIENDS = "FRIENDS";

    public static final String TAG = "FriendsFragment";
}