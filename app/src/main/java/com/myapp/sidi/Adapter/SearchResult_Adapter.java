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
                .load(arrayList.get(position).getUrl()).into(holder.url);

        holder.serverIndex.setText(arrayList.get(position).getServerIndex());
        holder.designNum.setText(arrayList.get(position).getDesignNum());
        holder.designCode.setText(arrayList.get(position).getDesignCode());
        holder.designName.setText(arrayList.get(position).getDesignName());
        holder.registerPerson.setText(arrayList.get(position).getRegisterPerson());
        holder.date_application.setText(arrayList.get(position).getDate_application());
        holder.date_registration.setText(arrayList.get(position).getDate_registration());
        holder.date_publication.setText(arrayList.get(position).getDate_publication());
        holder.dep_1.setText(arrayList.get(position).getDep_1());
        holder.dep_2.setText(arrayList.get(position).getDep_2());
        holder.dep_3.setText(arrayList.get(position).getDep_3());
        holder.dep_4.setText(arrayList.get(position).getDep_4());
        holder.dep_5.setText(arrayList.get(position).getDep_5());


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
        ImageView url;
        TextView serverIndex;
        TextView designNum;
        TextView designCode;
        TextView designName;
        TextView registerPerson;
        TextView date_application;
        TextView date_registration;
        TextView date_publication;
        TextView dep_1;
        TextView dep_2;
        TextView dep_3;
        TextView dep_4;
        TextView dep_5;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.url = itemView.findViewById(R.id.url);
            this.serverIndex = itemView.findViewById(R.id.serverIndex);
            this.designNum = itemView.findViewById(R.id.designNum);
            this.designCode = itemView.findViewById(R.id.designCode);
            this.designName = itemView.findViewById(R.id.designName);
            this.registerPerson = itemView.findViewById(R.id.registerPerson);
            this.date_application = itemView.findViewById(R.id.date_application);
            this.date_registration = itemView.findViewById(R.id.date_registration);
            this.date_publication = itemView.findViewById(R.id.date_publication);
            this.dep_1 = itemView.findViewById(R.id.dep_1);
            this.dep_2 = itemView.findViewById(R.id.dep_2);
            this.dep_3 = itemView.findViewById(R.id.dep_3);
            this.dep_4 = itemView.findViewById(R.id.dep_4);
            this.dep_5 = itemView.findViewById(R.id.dep_5);
        }
    }
}
