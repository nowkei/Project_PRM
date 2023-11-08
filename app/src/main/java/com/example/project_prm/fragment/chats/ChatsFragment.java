package com.example.project_prm.fragment.chats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.chatting.ChattingFragment;
import com.example.project_prm.model.Chats;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatsFragment extends Fragment {
    private ChatsCallBack chatsCallBack;
    private ChatsController chatsController;
    private ImageView img_chatAva;
    private TextView chatTitle;
    private TextView chatContent;
    private TextView chatTime;
    private RecyclerView recyclerView;
    private AdapterChats adapter;

    public ChatsFragment() {
        // Required empty public constructor
    }
    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    private void initAction(){

    }
    private void initView(){
        img_chatAva = getView().findViewById(R.id.img_chatAvatar);
        chatTitle = getView().findViewById(R.id.chat_title);
        chatContent = getView().findViewById(R.id.tvOtherUserChat);
        chatTime = getView().findViewById(R.id.chat_time);
        initRcvChats();
    }

    private void initRcvChats() {
        recyclerView = getView().findViewById(R.id.rcv_chats);
        adapter = new AdapterChats(new ArrayList<>(), new ChatsItemCallBack() {
            @Override
            public void getChatsItem(Chats chats) {
                if (getParentFragmentManager().findFragmentByTag(ChattingFragment.CHAT) == null)
                    addFragmentFragmentContainer(ChattingFragment.newInstance(chats), ChattingFragment.TAG);
            }
        }, requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    HashSet<Chats> chatSet = new HashSet<>();

    private void initObserver() {
        chatsCallBack = new ChatsCallBack() {
            @Override
            public void onChatsResult(boolean result, String message, ArrayList<Chats> chats) {
                if (result) {
                    chatSet.clear();
                    chatSet.addAll(chats);
                    adapter.changeDataSet(new ArrayList<>(chatSet));
                }
            }

            @Override
            public void onLoading(boolean isLoading) {
                if (getActivity() != null)
                    ((MainActivity) getActivity()).showLoading(isLoading);
            }

            @Override
            public void onChatUpdate(Chats chat) {

            }
        };
        chatsController = new ChatsController(chatsCallBack);
        getAllChatByUserId();
    }

    private void getAllChatByUserId() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
        String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
        chatsController.getChats(uid);
    }

    private void addFragmentFragmentContainer(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    public static final String TAG = "ChatsFragment";
}