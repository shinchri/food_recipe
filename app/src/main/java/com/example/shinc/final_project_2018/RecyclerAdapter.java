package com.example.shinc.final_project_2018;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Recipe> arrayList;
    ItemClicked activity;
    float textSize;


    public interface ItemClicked {
        void onItemClicked(int index);
    }

    public RecyclerAdapter(Context context, ArrayList<Recipe> arrayList, float textSize) {
        this.arrayList = arrayList;
        activity = (ItemClicked) context;
        this.textSize = textSize;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(arrayList.get(i));

        viewHolder.tvTitle.setText(arrayList.get(i).getTitle());
        viewHolder.tvTitle.setTextSize(textSize);

        String url = arrayList.get(i).getThumbnail();

        Log.d("chris", url);
        if(!url.equals("")) {
            Picasso.get().load(url).placeholder(R.drawable.ic_image_placeholder).error(R.drawable.ic_image_error).into(viewHolder.ivImage);
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        ImageView ivImage;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivImage = itemView.findViewById(R.id.ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(arrayList.indexOf((Recipe) v.getTag()));
                }
            });
        }
    }
}
