package com.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.DiaryEntity;
import com.example.diaryapp.R;
import com.example.diaryapp.databinding.ItemHomeBinding;

import java.util.ArrayList;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    private ArrayList<DiaryEntity> data = new ArrayList<>();
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setData(ArrayList<DiaryEntity> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHomeBinding binding = ItemHomeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaryEntity itemHome = data.get(position);
        holder.bindView(itemHome);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemHomeBinding binding;

        public ViewHolder(@NonNull ItemHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAbsoluteAdapterPosition());
                }
            });
        }

        public void bindView(DiaryEntity itemHome){
            binding.txtDate.setText(itemHome.getTitle());
            binding.txtTitle.setText(itemHome.getContent());

            byte[] imgBytes = itemHome.getImg();
            if (imgBytes != null && imgBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                binding.imgIcon.setImageBitmap(bitmap);
            } else {
                binding.imgIcon.setImageResource(R.drawable.calendar);
            }
        }

    }

}
