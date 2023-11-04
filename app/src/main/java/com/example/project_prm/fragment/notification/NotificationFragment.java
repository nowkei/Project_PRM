package com.example.project_prm.fragment.notification;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.model.Notification;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    private NotificationCallBack notificationCallBack;
    private NotificationController notificationController;
    private ImageView imgView;
    private TextView tvUsername;
    private TextView tvRq;
    private Button btnAcp;
    private Button btnDecline;
    private RecyclerView recyclerView;
    private ArrayList<Notification> notifications;
    private AdapterNotification adapter;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(ArrayList<Notification> notifications) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList( NOTIFICATIONS, notifications);
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
            Log.d("HaiLS", getArguments().getParcelableArrayList(NOTIFICATIONS).toString());
            notifications = getArguments().getParcelableArrayList(NOTIFICATIONS);
        }
    }
    private void initAction(){

    }
    private void initView() {
        ((MainActivity) getActivity()).showTitleBar(true, "Notifications");
        recyclerView = getView().findViewById(R.id.rcv_noti);
        initRecycleView(notifications);
    }
    private void initObserver(){
        notificationCallBack = new NotificationCallBack() {
            @Override
            public void onLoading(boolean isLoading) {
                ((MainActivity) getActivity()).showLoading(isLoading);
            }

            @Override
            public void onNotificationChange(ArrayList<Notification> notifications) {
                adapter.changeDataSet(notifications);
            }
        };
        notificationController = new NotificationController(notificationCallBack);
    }

    private void initRecycleView(ArrayList<Notification> notifications) {
        NotificationItemCallBack callBack = new NotificationItemCallBack() {
            @Override
            public void onButtonClick(String button, int position) {
                if (button.equals("Acp")) {

                } else {
                    notificationController.removeNotification(position, notifications);
                }
            }
        };
        adapter = new AdapterNotification(notifications, callBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false ));
        recyclerView.setAdapter(adapter);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    public static final String TAG = "NotificationFragment";
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
}