package com.example.chatapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.yalantis.ucrop.UCrop;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class Profile_page extends AppCompatActivity {

    ImageView profilePic;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST=1;
    EditText usernameInput;
    EditText phoneInput;
    Button updateProfileBtn;
    ProgressBar progressBar;
    UserModel currentUserModel;
    Uri selectedImageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        profilePic=findViewById(R.id.profile_image_view);
        usernameInput=findViewById(R.id.profile_username);
        phoneInput=findViewById(R.id.profile_phone);
        updateProfileBtn=findViewById(R.id.profle_update_btn);
        progressBar=findViewById(R.id.profile_progress_bar);

        getUserData();


        updateProfileBtn.setOnClickListener((v -> {
            updateBtnClick();
        }));

        profilePic.setOnClickListener((v)->{
            onChooseImageClick(v);
        });

    }
    public void onChooseImageClick(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri= data.getData();

            // Start UCrop for image cropping
            startCropActivity(imageUri);
        }else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            // Image cropped successfully, get the cropped URI
            Uri resultUri = UCrop.getOutput(data);
            // Now you can proceed with uploading the cropped image
            if (resultUri != null) {
                profilePic.setImageURI(resultUri);
                selectedImageUri=resultUri;
                //uploadImage(resultUri);
            }
        }
    }
    private void startCropActivity(Uri sourceUri) {
        // UCrop configuration
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(80);

        // Start UCrop activity
        UCrop.of(sourceUri, Uri.fromFile(new File(getCacheDir(), "cropped_image.jpg")))
                .withOptions(options)
                .start(Profile_page.this);
    }

    void updateBtnClick() {
        String newUsername = usernameInput.getText().toString();
        if (newUsername.isEmpty() || newUsername.length() < 3) {
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        currentUserModel.setUsername(newUsername);
        setInProgress(true);


        if (selectedImageUri != null) {
            FirebaseUtil.getCurrentProfilePicStorageRef().putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        updateToFirestore();
                    });
        } else {
            updateToFirestore();
        }
    }

    void updateToFirestore(){
        FirebaseUtil.currentUserDetails().set(currentUserModel)
                .addOnCompleteListener(task -> {
                    setInProgress(false);
                    if(task.isSuccessful()){
                        Toast.makeText(this,"Updated successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Updated failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }


    void getUserData(){
        setInProgress(true);

        FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Uri uri  = task.getResult();
                        AndroidUtil.setProfilePic(this,uri,profilePic);
                    }
                });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            setInProgress(false);
            currentUserModel = task.getResult().toObject(UserModel.class);
            usernameInput.setText(currentUserModel.getUsername());
            phoneInput.setText(currentUserModel.getPhone());
        });
    }


    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            updateProfileBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            updateProfileBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Profile_page.this, Home_page.class);
        startActivity(intent);
        finish();
    }
}