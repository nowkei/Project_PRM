package com.example.project_prm.fragment.chatting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.model.Chats;
import com.example.project_prm.model.Message;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;

public class ChattingFragment extends Fragment {

    private RecyclerView rcvChatting;

    private AdapterChatting chattingAdapter;

    private ChattingController chattingController;

    private Button btnSend;

    private EditText edtMessage;

    private ImageView imvBack;

    private TextView tvMessageTitle;

    private ImageView imvChatAvatar;

    public ChattingFragment() {
        // Required empty public constructor
    }

    public static ChattingFragment newInstance(Chats chats) {
        ChattingFragment fragment = new ChattingFragment();
        Bundle args = new Bundle();
        args.putParcelable(CHAT, chats);
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
                initObserver();
                initAction();
            }
        });
    }

    private void initView() {
        ((MainActivity) getActivity()).showTitleBar(false, "Chats");
        btnSend = getView().findViewById(R.id.btnSend);
        edtMessage = getView().findViewById(R.id.inputChatting);
        imvBack = getView().findViewById(R.id.imvBack);
        tvMessageTitle = getView().findViewById(R.id.tvTabTitle);
        imvChatAvatar = getView().findViewById(R.id.imvChatAvatar);
        if (getArguments() != null) {
            Chats chats = getArguments().getParcelable(CHAT);
            initRcvChatting(chats);
            Glide.with(requireContext())
                    .load(chats.getAvatar())
                    .placeholder(R.drawable.userimage)
                    .error(R.drawable.userimage)
                    .circleCrop().into(imvChatAvatar);
            SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
            String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
            if (chats.getToUserId().equalsIgnoreCase(uid)) {
                tvMessageTitle.setText(chats.getFromUserName());
            } else {
                tvMessageTitle.setText(chats.getToUserName());
            }
        }
    }

    private void initRcvChatting(Chats chats) {
        chattingAdapter = new AdapterChatting(new ArrayList<>(), chats, requireContext());
        rcvChatting = getView().findViewById(R.id.rcv_chats);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        rcvChatting.setLayoutManager(linearLayoutManager);
        rcvChatting.setAdapter(chattingAdapter);
    }

    private void initAction() {
        edtMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                rcvChatting.smoothScrollToPosition(chattingAdapter.getItemCount());
                return false;
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null) {
                    SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
                    String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                    Chats chat = getArguments().getParcelable(CHAT);
                    if (!edtMessage.getText().toString().isEmpty()) {
                        chattingController.addNewMessage(chat, edtMessage.getText().toString().trim(), uid);
                        edtMessage.setText("");
                    }
                }
            }
        });

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

    }

    private void initObserver() {
        chattingController = new ChattingController(new ChattingCallback() {
            @Override
            public void onSendMessage(boolean status, String message) {
                if (!status) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMessageChange(boolean status, String message, ArrayList<Message> messages) {
                if (status) {
                    chattingAdapter.changeDataSet(messages);
                    rcvChatting.smoothScrollToPosition(chattingAdapter.getItemCount());
                } else {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (getArguments() != null) {
            Chats chat = getArguments().getParcelable(CHAT);
            chattingController.getMessageList(chat);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatting, container, false);
    }

    public static final String TAG = "ChattingFragment";

    public static final String CHAT = "CHAT";
}