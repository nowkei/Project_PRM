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

import com.example.project_prm.R;
import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.model.Notification;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initAction();
        initObserver();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    private void initAction(){

    }
    private void initView(){
        recyclerView = getView().findViewById(R.id.rcv_noti);
    }
    private void initObserver(){
        notificationCallBack = new NotificationCallBack() {
            @Override
            public void onNotificationResult(boolean result, String message, ArrayList<Notification> notifications) {
                if(result) {
                    initRecycleView(notifications);
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onLoading(boolean isLoading) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                if (isLoading) {
                    DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
                    loadingDialog.show(fragmentManager, DialogLoadingFragment.TAG);
                } else {
                    DialogLoadingFragment dialogLoadingFragment = (DialogLoadingFragment) fragmentManager.findFragmentByTag(DialogLoadingFragment.TAG);
                    dialogLoadingFragment.dismiss();
                }
            }

            @Override
            public void onNotificationChange(ArrayList<Notification> notifications) {
                adapter.changeDataSet(notifications);
            }
        };
        notificationController = new NotificationController(notificationCallBack);
        notificationController.getNotification();
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
}