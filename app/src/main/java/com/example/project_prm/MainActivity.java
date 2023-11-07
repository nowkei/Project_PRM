package com.example.project_prm;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.project_prm.component.DialogLoadingFragment;
import com.example.project_prm.fragment.home.HomeFragment;
import com.example.project_prm.fragment.login.LoginFragment;
import com.example.project_prm.fragment.setting.DialogChangeUserImage.DialogChangeUserImageFragment;
import com.example.project_prm.fragment.setting.SettingFragment;
import com.example.project_prm.fragment.setting.profile.SettingProfileFragment;
import com.example.project_prm.fragment.welcome.WelcomeFragment;
import com.example.project_prm.util.SharedPreferencesKey;
import com.example.project_prm.util.SharedPreferencesUtil;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DialogChangeUserImageFragment.OnImageCaptureListener, DialogChangeUserImageFragment.OnPhotoCaptureListener {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 101;
    private static final int GALLERY_REQUEST_CODE = 102;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CardView cvTabTitle;

    private TextView tvTabTitle;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
    }

    private void initAction() {

    }

    private void initView() {
        cvTabTitle = findViewById(R.id.cvTabTitle);
        tvTabTitle = findViewById(R.id.tvTabTitle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.anim_slide_in,
                R.anim.anim_fade_out,
                R.anim.anim_fade_in,
                R.anim.anim_slide_out)
                .setReorderingAllowed(true);
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
        if (sharedPreferencesUtil.getData(SharedPreferencesKey.USERID) != null && !sharedPreferencesUtil.getData(SharedPreferencesKey.USERID).isEmpty()) {
            //fragmentTransaction.addToBackStack(HomeFragment.TAG);
            fragmentTransaction.add(R.id.fragmentContainer, HomeFragment.newInstance(), HomeFragment.TAG)
                    .commit();
        } else {
            //fragmentTransaction.addToBackStack(WelcomeFragment.TAG);
            fragmentTransaction.add(R.id.fragmentContainer, WelcomeFragment.newInstance(), WelcomeFragment.TAG)
                    .commit();
        }
    }

    public void showLoading(boolean isLoading) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isLoading) {
            DialogLoadingFragment loadingDialog = new DialogLoadingFragment();
            loadingDialog.show(fragmentManager, DialogLoadingFragment.TAG);
        } else {
            DialogLoadingFragment dialogLoadingFragment = (DialogLoadingFragment) fragmentManager.findFragmentByTag(DialogLoadingFragment.TAG);
            if (dialogLoadingFragment != null)
                dialogLoadingFragment.dismiss();
        }
    }

    public void showTitleBar(boolean isShow, String content) {
        if (!isShow) {
            cvTabTitle.setVisibility(View.GONE);
        } else {
            cvTabTitle.setVisibility(View.VISIBLE);
            tvTabTitle.setText(content);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onPhotoCaptureRequested(){
//        if( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);
//        }else{
            Intent photoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(photoIntent, GALLERY_REQUEST_CODE);
//        }
    }

    @Override
    public void onImageCaptureRequested() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent photoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoIntent, GALLERY_REQUEST_CODE);
            }
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                uploadImageToFirebaseStorage(photo);
            }
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                uploadImageToFirebaseStorage(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Lỗi khi đọc ảnh từ thư viện", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImageToFirebaseStorage(Bitmap photo) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + ".jpg";
        StorageReference imageRef = storageRef.child(imageFileName);
        ByteArrayOutputStream convertToByte = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, convertToByte);
        byte[] imageData = convertToByte.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageData);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(getApplicationContext());
                String uid = sharedPreferencesUtil.getData(SharedPreferencesKey.USERID);
                sharedPreferencesUtil.addOrUpdateData(SharedPreferencesKey.AVATAR, imageUrl);
                String avatar = sharedPreferencesUtil.getData(SharedPreferencesKey.AVATAR);
                MainController.UpLoadImageToFirebase(uid,imageUrl);
                FragmentManager fragmentManager = getSupportFragmentManager();

                Fragment settingProfileFragment = fragmentManager.findFragmentByTag("SettingProfileFragment");
                Fragment settingFragment = fragmentManager.findFragmentByTag("SettingFragment");
                    View profileView = settingProfileFragment.getView();
                    View settingView = settingFragment.getView();
                    ImageView userProfileImageView = profileView.findViewById(R.id.userImage);
                    ImageView userProfileBackgroundImageView = profileView.findViewById(R.id.imageBackground);
                    ImageView userSettingImageView = settingView.findViewById(R.id.img_user_layout_setting);
                    ImageView userSettingBackgroundImageView = settingView.findViewById(R.id.img_user_background_setting);
                Glide.with(getApplicationContext()).load(avatar).into(userProfileImageView);
                Glide.with(getApplicationContext()).load(avatar).into(userProfileBackgroundImageView);
                Glide.with(getApplicationContext()).load(avatar).into(userSettingImageView);
                Glide.with(getApplicationContext()).load(avatar).into(userSettingBackgroundImageView);

            });
        }).addOnFailureListener(e -> {
           Toast.makeText(this, "failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });

        Log.d("1234", "uploadImageToFirebaseStorage: "+SharedPreferencesKey.AVATAR );


    }






    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}