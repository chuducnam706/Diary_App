package com.example;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.data.AppDatabase;
import com.example.data.DataManager;
import com.example.data.DiaryDao;
import com.example.data.DiaryEntity;
import com.example.diaryapp.R;
import com.example.diaryapp.databinding.ActivityHomeBinding;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    private ActivityHomeBinding binding;
    private HomeItemAdapter adapter;
    private AppDatabase appDatabase;
    private DiaryDao diaryDao;
    private ArrayList<DiaryEntity> entities = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new HomeItemAdapter();
        binding.txtCard.setAdapter(adapter);

        initData();
        adapter.setListener(this);

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH || i == EditorInfo.IME_ACTION_DONE) {
                    search();
                    return true;
                }
                return false;
            }
        });

        binding.btnRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp(view);
            }
        });

        binding.btnNewDiary.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, NewDiaryActivity.class);
            intent.putExtra("pass", "addnew");
            LocalDateTime nowDateTime = LocalDateTime.now();
            long now = nowDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            String formatted = nowDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            intent.putExtra("time", formatted);
            startActivity(intent);
        });

    }

    private void initData() {
        appDatabase = DataManager.getInstance().createDataBase(this);
        diaryDao = appDatabase.diaryDao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        entities.clear();
        entities.addAll(new ArrayList<>(diaryDao.getAllDiaryEntities()));
        adapter.setData(entities);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, NewDiaryActivity.class);
        intent.putExtra("put", entities.get(position).getId());
        startActivity(intent);
    }

    private void search() {

        String keyWorld = binding.edtSearch.getText().toString().trim();

        if (keyWorld.isEmpty()) {
            Toast.makeText(this, "write down infomation ", Toast.LENGTH_SHORT).show();
            entities.addAll(new ArrayList<>(diaryDao.getAllDiaryEntities()));
            adapter.setData(entities);
        }

        entities.clear();
        entities.addAll(diaryDao.search(keyWorld));
        adapter.setData(entities);

        if (entities.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void popUp(View anchor){
        PopupMenu popupMenu = new PopupMenu(this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
        popupMenu.show();
    }


}


