package com.example;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.data.AppDatabase;
import com.example.data.DataManager;
import com.example.data.DiaryDao;
import com.example.data.DiaryEntity;
import com.example.diaryapp.R;
import com.example.diaryapp.databinding.ActivityNewDiaryBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class NewDiaryActivity extends AppCompatActivity {

    private ActivityNewDiaryBinding binding;
    private AppDatabase appDatabase;
    private DiaryDao diaryDao;
    private DiaryEntity entity = new DiaryEntity();
    private String get;
    private String time;
    private static final int CAMERA_PERMISSION_CODE = 99;
    private static final int REQUEST_GALLERY_PERMISSION = 101;
    private byte[] imageBytes = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewDiaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeEvent();
        initializeData();
        bindView();
        binding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupImage(view);
            }
        });

        binding.edtContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(binding.edtContent.getWindowToken(), 0);

                return true;
            }
            return false;
        });
    }

    private void initializeEvent() {
        binding.btnBack.setOnClickListener(view -> finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.btnSave.setOnClickListener(view -> saveEntity());
        }
        binding.btnDelete.setOnClickListener(view -> deleteEntity(entity));
    }

    private void initializeData() {
        int id = getIntent().getIntExtra("put", -1);
        get = getIntent().getStringExtra("pass");
        time = getIntent().getStringExtra("time");

        if (id != -1) {
            appDatabase = DataManager.getInstance().createDataBase(this);
            diaryDao = appDatabase.diaryDao();
            entity = diaryDao.getDiaryById(id);
        }

        get = getIntent().getStringExtra("pass");
        time = getIntent().getStringExtra("time");

    }


    private void bindView() {
        binding.txtTitle.setText(time + "");
        if (entity != null) {
            binding.edtTitle.setText(entity.getTitle());
            binding.edtContent.setText(entity.getContent());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime dateTime = Instant.ofEpochMilli(entity.getDateTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();
                String formatted = dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                binding.txtTitle.setText(formatted);
            }

            if (entity.getImg() != null) {
                imageBytes = entity.getImg();
                Bitmap bitmap = bytesToBitmap(imageBytes);
                binding.imageBackground.setImageBitmap(bitmap);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveEntity() {
        long now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String title = binding.edtTitle.getText().toString();
        String content = binding.edtContent.getText().toString();
        if (get == null) {
            entity.setTitle(title);
            entity.setContent(content);
            entity.setDateTime(now);
            entity.setImg(imageBytes);
            appDatabase = DataManager.getInstance().createDataBase(this);
            diaryDao = appDatabase.diaryDao();
            diaryDao.upDateData(entity);
            Toast.makeText(this, "update success", Toast.LENGTH_SHORT).show();
        } else {
            appDatabase = DataManager.getInstance().createDataBase(this);
            diaryDao = appDatabase.diaryDao();
            DiaryEntity duLieu = new DiaryEntity();
            duLieu.setTitle(title);
            duLieu.setContent(content);
            duLieu.setDateTime(now);
            duLieu.setImg(imageBytes);
            diaryDao.insertAll(duLieu);
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void deleteEntity(DiaryEntity entity) {
        new AlertDialog.Builder(this)
                .setTitle("do you want to delete item")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        appDatabase = DataManager.getInstance().createDataBase(NewDiaryActivity.this);
                        diaryDao = appDatabase.diaryDao();
                        diaryDao.delete(entity);
                        finish();
                        Toast.makeText(NewDiaryActivity.this, "delete success", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", null).show();
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null) return;

        if (requestCode == CAMERA_PERMISSION_CODE) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            binding.imageBackground.setImageBitmap(bitmap);
            imageBytes = bitmapToBytes(bitmap);
        } else if (requestCode == 101) {
            Uri uri = data.getData();
            binding.imageBackground.setImageURI(uri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageBytes = bitmapToBytes(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showPopupImage(View anchor) {
        PopupMenu popupMenu = new PopupMenu(NewDiaryActivity.this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.popup_image, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.camera) {
                openCamera();
                return true;
            } else {
                openImage();
                return false;
            }
        });
        popupMenu.show();

    }

        private void openCamera() {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(NewDiaryActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                return;
            }

            Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 99);
        }

    private void openImage() {
        if (ActivityCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_GALLERY_PERMISSION);
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,30, stream);
        return stream.toByteArray();
    }

    private Bitmap bytesToBitmap(byte[] bytes) {
        return android.graphics.BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


}
