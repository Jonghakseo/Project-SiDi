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
import com.myapp.sidi.DTO.SketchListData;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class SketchListAdapter extends RecyclerView.Adapter<SketchListAdapter.CustomViewHolder> {
    private ArrayList<SketchListData> arrayList;
    private OnItemClickListener mListener = null ;
    private Context context;

    public SketchListAdapter(ArrayList<SketchListData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sketch_list_item,parent,false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView).load(arrayList.get(position).getUrl()).into(holder.url);

        holder.uploadUser.setText("등록인:"+String.valueOf(arrayList.get(position).getUploadUser())+"님");

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView url;
        TextView uploadUser;


        CustomViewHolder(@NonNull final View itemView) {
            super(itemView);
            url = itemView.findViewById(R.id.iv_image);
            uploadUser = itemView.findViewById(R.id.tv_name);

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