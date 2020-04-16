package com.myapp.sidi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.DTO.SearchDetailData;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class SearchDetail_Adapter extends RecyclerView.Adapter<SearchDetail_Adapter.CustomViewHolder> {
   private ArrayList<SearchDetailData> arrayList;
   private OnItemClickListener mListener = null ;
   private Context context;

    public SearchDetail_Adapter(ArrayList<SearchDetailData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_detail_design_item,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getDesign()).into(holder.iv_design);
        holder.tv_designId.setText(String.valueOf(arrayList.get(position).getDesignId()));
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

        CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            iv_design = itemView.findViewById(R.id.iv_design);
            tv_designId = itemView.findViewById(R.id.tv_designId);

            iv_design.setOnClickListener(new View.OnClickListener() {
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
