package com.example.fishfolio;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private static final String PREFS_NAME = "AppPrefs";
    private static final String PROFILE_PIC_URL_KEY = "profile_pic_url_";
    private CircleImageView userProfilePic;
    private SharedPreferences prefs;
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView tvFullName, tvEmail, tvGender, tvUserid;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvGender = rootView.findViewById(R.id.tvGender);
        tvEmail = rootView.findViewById(R.id.tvEmail);
        tvUserid = rootView.findViewById(R.id.tvUserId);
        userProfilePic = rootView.findViewById(R.id.userProfilePic);
        prefs = getActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);


        database.getReference().child("Users").child(user.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Users currentUser = task.getResult().getValue(Users.class);
                if (currentUser != null){
                    tvFullName.setText(currentUser.getName());
                    tvEmail.setText(currentUser.geteMail());
                    tvGender.setText(currentUser.getGender());
                    tvUserid.setText(user.getUid());
                }
            }
        });

        loadProfilePicFromPrefs();

        // Copy User ID
        tvUserid.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                Drawable drawableRight = tvUserid.getCompoundDrawables()[2]; // Right drawable is at index 2
                if (drawableRight != null) {
                    if (!drawableRight.getBounds().isEmpty()) {
                        drawableRight.setBounds(0, 0, drawableRight.getIntrinsicWidth(), drawableRight.getIntrinsicHeight());
                    }
                    int drawableWidth = drawableRight.getBounds().width();
                    // Check if the touch event is within the bounds of the right drawable
                    if (event.getX() >= (tvUserid.getWidth() - tvUserid.getPaddingRight() - drawableWidth)) {
                        // Right drawable clicked
                        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("Copy",tvUserid.getText().toString());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
            return false;
        });


        return rootView;
    }

    private void loadProfilePicFromPrefs() {
        String userId = auth.getCurrentUser().getUid(); // Get the current user's unique ID
        String profilePicUrl = prefs.getString(PROFILE_PIC_URL_KEY + userId, null); // Load the user-specific URL from SharedPreferences

        if (profilePicUrl != null) {
            // Load the image using Glide if the URL exists
            Glide.with(this)
                    .load(profilePicUrl)
                    .placeholder(R.drawable.profilephoto) // Default placeholder if the image is not available
                    .circleCrop()
                    .into(userProfilePic);
        } else {
            // Optionally, handle cases where the image URL is not found (show default image)
            userProfilePic.setImageResource(R.drawable.profilephoto);
        }
    }
}