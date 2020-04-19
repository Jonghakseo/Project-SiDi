package com.myapp.sidi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.DTO.SearchDetailData;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class SearchSketch_Adapter extends RecyclerView.Adapter<SearchSketch_Adapter.CustomViewHolder> {
    private ArrayList<SearchDetailData> arrayList;
    private OnItemClickListener mListener = null ;
    private Context context;

    public SearchSketch_Adapter(ArrayList<SearchDetailData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_sketch_design_item,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getDesign()).into(holder.iv_similar);
        if (arrayList.get(position).getDesignId() > 85){
            holder.tv_simRate.setTextColor(Color.GREEN);
        }else if (arrayList.get(position).getDesignId() > 75){
            holder.tv_simRate.setTextColor(Color.RED);
        }else {
            holder.tv_simRate.setTextColor(Color.DKGRAY);
        }
        holder.tv_simRate.setText("유사도 "+String.valueOf(arrayList.get(position).getDesignId())+"%");
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
        ImageView iv_similar;
        TextView tv_simRate;


        CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            iv_similar = itemView.findViewById(R.id.iv_similar);
            tv_simRate = itemView.findViewById(R.id.tv_simRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        mListener.onItemClick(v,pos);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }
}