package com.example.mymanager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    Context context;

    ArrayList<Diary> diaries;


    public DiaryAdapter(Context context, ArrayList<Diary> diaries) {
        this.context = context;
        this.diaries = diaries;
    }

    @NonNull
    @Override
    public DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_note_m,parent,false);
        return new DiaryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.DiaryViewHolder holder, int position) {
        holder.setDiary(diaries.get(position));
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textEntry,textDateTime;
        ImageView imageNote;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textEntry = itemView.findViewById(R.id.textEntry);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            imageNote = itemView.findViewById(R.id.imageNote);
        }

        void setDiary(Diary diary){
            textTitle.setText(diary.getTitle());
            textEntry.setText(diary.getEntry());
            textDateTime.setText(diary.getDateTime());

            if(diary.getImagePath() != null){
                imageNote.setImageBitmap(BitmapFactory.decodeFile(diary.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            }else {
                imageNote.setVisibility(View.GONE);
            }

        }


    }




}

