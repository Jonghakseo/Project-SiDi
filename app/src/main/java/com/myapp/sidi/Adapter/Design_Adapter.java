package com.myapp.sidi.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.DTO.Design_Data;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class Design_Adapter extends RecyclerView.Adapter<Design_Adapter.CustomViewHolder> {
    private ArrayList<Design_Data> arrayList;
    private Context context;

    public Design_Adapter(ArrayList<Design_Data> arrayList ,Context context){
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public Design_Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_design_item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Design_Adapter.CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getUrl()).into(holder.iv_url);

        holder.tv_designCode.setText(arrayList.get(position).getDesignCode());
        holder.tv_tag_1.setText(arrayList.get(position).getTag_1());
        holder.tv_tag_2.setText(arrayList.get(position).getTag_2());
        holder.tv_tag_3.setText(arrayList.get(position).getTag_3());

        holder.itemView.setTag(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_url;
        TextView tv_designCode;
        TextView tv_tag_1;
        TextView tv_tag_2;
        TextView tv_tag_3;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_url = itemView.findViewById(R.id.iv_url);
            this.tv_designCode = itemView.findViewById(R.id.tv_designCode);
            this.tv_tag_1 = itemView.findViewById(R.id.tv_tag_1);
            this.tv_tag_2 = itemView.findViewById(R.id.tv_tag_2);
            this.tv_tag_3 = itemView.findViewById(R.id.tv_tag_3);
        }
    }
}
