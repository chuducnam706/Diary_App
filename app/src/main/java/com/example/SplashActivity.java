package com.example;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.diaryapp.R;
import com.example.diaryapp.databinding.ActivitySplashBinding;

import java.io.File;
import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(this).load(R.drawable.fllower).into(binding.imgflower);
        splash();

    }


    private void splash() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);

        File parent = getExternalFilesDir(null);
        File file = new File(parent, "hello");
        File child = new File(file.getAbsoluteFile(), "child.txt");
        file.mkdir();
        child.mkdir();


        try {
            child.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            if (isFirstRun) {
//                Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
//                startActivity(intent);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("isFirstRun", false);
//                editor.apply();
//            } else {
//                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        }, 3000);


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.start();
    }

}
