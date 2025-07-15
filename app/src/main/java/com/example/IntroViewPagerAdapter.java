package com.example;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diaryapp.databinding.ItemIntroBinding;

import java.util.ArrayList;

public class IntroViewPagerAdapter extends RecyclerView.Adapter<IntroViewPagerAdapter.ViewHolder> {

    private ArrayList<Intro> data = new ArrayList<>();
    private ItemIntroBinding binding;

    public void setData(ArrayList<Intro> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyItemChanged(0, data.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemIntroBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intro intro = data.get(position);
        holder.bindView(intro);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemIntroBinding binding;

        public ViewHolder(@NonNull ItemIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Intro intro) {

            binding.imgSlide.setImageResource(Integer.parseInt(intro.getImageSrc()+""));
            binding.txtTitle.setText(intro.getTitle());
            binding.txtContent.setText(intro.getContent());
        }

    }

}
