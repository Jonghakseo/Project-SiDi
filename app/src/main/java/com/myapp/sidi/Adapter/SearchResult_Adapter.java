package com.myapp.sidi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.DTO.SearchResultData;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class SearchResult_Adapter extends RecyclerView.Adapter<SearchResult_Adapter.CustomViewHolder> {
   private ArrayList<SearchResultData> arrayList;
   private Context context;

    public SearchResult_Adapter(ArrayList<SearchResultData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_design_item,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getDesign()).into(holder.iv_design);
        holder.tv_designId.setText(arrayList.get(position).getDesignId());
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
        ImageView iv_design;
        TextView tv_designId;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_design = itemView.findViewById(R.id.iv_design);
            this.tv_designId = itemView.findViewById(R.id.tv_designId);
        }
    }
}
