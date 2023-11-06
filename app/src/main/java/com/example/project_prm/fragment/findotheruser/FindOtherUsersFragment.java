package com.example.project_prm.fragment.findotheruser;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project_prm.MainActivity;
import com.example.project_prm.R;
import com.example.project_prm.fragment.userprofile.UserProfileFragment;
import com.example.project_prm.model.Info;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;

import java.util.ArrayList;

public class FindOtherUsersFragment extends Fragment {

    private RecyclerView rcvFindOtherUsers;

    private EditText edtSearch;

    private TextView tvNoUserFound;

    private FindOtherUserAdapter findOtherUserAdapter;

    private FindOtherUsersController findOtherUsersController;

    public FindOtherUsersFragment() {
        // Required empty public constructor
    }
    public static FindOtherUsersFragment newInstance() {
        FindOtherUsersFragment fragment = new FindOtherUsersFragment();
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
        edtSearch = getView().findViewById(R.id.edtFindOtherUser);
        initRcvFindOtherUsers();
        tvNoUserFound = getView().findViewById(R.id.tvNoUserFound);
    }

    private void initRcvFindOtherUsers() {
        rcvFindOtherUsers = getView().findViewById(R.id.rcvOtherUser);
        FindOtherUserItemCallBack findOtherUserItemCallBack = new FindOtherUserItemCallBack() {
            @Override
            public void onItemClick(Info info) {
                addFragmentFromFragmentContainerHost(UserProfileFragment.newInstance(info), UserProfileFragment.TAG);
                hideKeyboardFrom(getContext(), getView());
                edtSearch.setText("");
            }
        };
        findOtherUserAdapter = new FindOtherUserAdapter(new ArrayList<>(), findOtherUserItemCallBack, requireContext());
        rcvFindOtherUsers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false ));
        rcvFindOtherUsers.setAdapter(findOtherUserAdapter);
    }

    public void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initAction() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(requireContext());
                String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                String userName = sharedPreferencesUtil.getData(SharedPreferencesKey.USERNAME);
                findOtherUsersController.findOtherUser(edtSearch.getText().toString(), uid, "", userName);
            }
        });
    }

    private void initObserver() {
        findOtherUsersController = new FindOtherUsersController(new FindOtherUsersCallback() {
            @Override
            public void onFindFriend(boolean status, String message, ArrayList<Info> u) {
                if (u.isEmpty()) {
                    rcvFindOtherUsers.setVisibility(View.GONE);
                    tvNoUserFound.setVisibility(View.VISIBLE);
                } else {
                    tvNoUserFound.setVisibility(View.GONE);
                    rcvFindOtherUsers.setVisibility(View.VISIBLE);
                    findOtherUserAdapter.changeDataSet(u);
                }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_other_users, container, false);
    }

    public static final String TAG = "FindOtherUsersFragment";
}