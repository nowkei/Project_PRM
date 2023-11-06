package com.example.project_prm.fragment.setting.DialogChangeUserImage;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.project_prm.R;


public class DialogChangeUserImageFragment extends DialogFragment {

    public interface OnImageCaptureListener {
        void onImageCaptureRequested();
    }
    private OnImageCaptureListener onImageCaptureListener;

    public void setOnImageCaptureListener(OnImageCaptureListener listener) {
        this.onImageCaptureListener = listener;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_change_user_image, container, false);
        TextView takeImage = rootView.findViewById(R.id.txtTakeImage);

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onImageCaptureListener != null) {
                    onImageCaptureListener.onImageCaptureRequested();
                }
            }
        });

        return rootView;
    }

    public static final String TAG = "DialogLoadImage";
}