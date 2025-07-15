    package com.example;
    
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    
    import com.example.diaryapp.R;
    import com.example.diaryapp.databinding.ActivityIntroBinding;
    
    import java.util.ArrayList;
    
    public class IntroActivity extends AppCompatActivity {
    
        private ActivityIntroBinding binding;
        private IntroViewPagerAdapter adapter;
        private ArrayList<Intro> data;
    
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityIntroBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
    
            initData();
            adapter = new IntroViewPagerAdapter();
            binding.viewPager.setAdapter(adapter);
            adapter.setData(data);
            binding.dotsIndicator.attachTo(binding.viewPager);
    
            binding.btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (binding.viewPager.getCurrentItem() == 3) {
                        Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        int currentItem = binding.viewPager.getCurrentItem();
                        binding.viewPager.setCurrentItem(currentItem + 1);
                    }
                }
            });
    
        }
    
        public void initData() {
            data = new ArrayList<>();
            data.add(new Intro(R.drawable.intro1, "Customize Your Journal", "Personalize Your Diary with a Variety of Emojis, Stickers, and Backgrounds"));
            data.add(new Intro(R.drawable.intro2, "Secure Journal Keeper", "Protect Your Diary with Password Security"));
            data.add((new Intro(R.drawable.intro3, "Emotion Journal", "Monitor Your Emotional Journey Every Day")));
            data.add(new Intro(R.drawable.intro4, "Track expenses", "Effortlessly Monitor and Control Your Spending"));
        }
    
    
    }
