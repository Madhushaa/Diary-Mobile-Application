package com.example.mymanager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SecretAdapter  extends RecyclerView.Adapter<SecretAdapter.SecretViewHolder>{

    Context context;

    ArrayList<SecretDiary> secretDiaries;

    public SecretAdapter(Context context, ArrayList<SecretDiary> secretDiaries) {
        this.context = context;
        this.secretDiaries = secretDiaries;
    }

    @NonNull
    @Override
    public SecretAdapter.SecretViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_container_note_m,parent,false);
        return new SecretViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SecretAdapter.SecretViewHolder holder, int position) {

        holder.setSecret(secretDiaries.get(position));
    }

    @Override
    public int getItemCount() {
        return secretDiaries.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class SecretViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle,textEntry,textDateTime;
        ImageView imageNote;


        public SecretViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textEntry = itemView.findViewById(R.id.textEntry);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            imageNote = itemView.findViewById(R.id.imageNote);
        }

        void setSecret(SecretDiary secretDiary){
            textTitle.setText(secretDiary.getTitle());
            textEntry.setText(secretDiary.getEntry());
            textDateTime.setText(secretDiary.getDateTime());

            if(secretDiary.getImagePath() != null){
                imageNote.setImageBitmap(BitmapFactory.decodeFile(secretDiary.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            }else {
                imageNote.setVisibility(View.GONE);
            }

        }
    }
}
