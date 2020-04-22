package com.myapp.sidi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.myapp.sidi.Category.CountryInfo;
import com.myapp.sidi.DTO.Detail_SimilarDesignRcy_Data;
import com.myapp.sidi.R;

import java.util.ArrayList;

public class Detail_SimilarDesignList_Adapter extends RecyclerView.Adapter<Detail_SimilarDesignList_Adapter.CustomViewHolder> {
    private ArrayList<Detail_SimilarDesignRcy_Data> arrayList;
    private OnItemClickListener mListener = null;
    private Context context;

    public Detail_SimilarDesignList_Adapter(ArrayList<Detail_SimilarDesignRcy_Data> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_design_item, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getUrl()).into(holder.url);
        CountryInfo countryInfo = new CountryInfo();
        System.out.println(arrayList.get(position).getCountry());
        System.out.println(arrayList.get(position).getCountry().equals(countryInfo.getKor()));
        if (arrayList.get(position).getCountry().equals(countryInfo.getKor())) {
            Glide.with(holder.itemView).load(R.drawable.d_kor).into(holder.country);
        } else if (arrayList.get(position).getCountry().equals(countryInfo.getJap())) {
            Glide.with(holder.itemView).load(R.drawable.d_jap).into(holder.country);
        } else if (arrayList.get(position).getCountry().equals(countryInfo.getUsa())) {
            Glide.with(holder.itemView).load(R.drawable.d_usa).into(holder.country);
        } else if (arrayList.get(position).getCountry().equals(countryInfo.getGer())) {
            Glide.with(holder.itemView).load(R.drawable.d_ger).into(holder.country);
        } else if (arrayList.get(position).getCountry().equals(countryInfo.getWipo())) {
            Glide.with(holder.itemView).load(R.drawable.d_wipo).into(holder.country);
        } else if (arrayList.get(position).getCountry().equals(countryInfo.getOhim())) {
            Glide.with(holder.itemView).load(R.drawable.d_ohim).into(holder.country);
        }


        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView url;
        ImageView country;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.url = itemView.findViewById(R.id.iv_design);
            this.country = itemView.findViewById(R.id.iv_country);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        mListener.onItemClick(v, pos);
                    }
                }
            });
        }
    }
}