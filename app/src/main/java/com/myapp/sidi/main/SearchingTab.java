package com.myapp.sidi.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ViewUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.myapp.sidi.R;
import com.myapp.sidi.search.SearchResult;

public class SearchingTab extends AppCompatActivity {
    private Button btn_slideFurniture,btn_slideTime,btn_slideNation,btn_slideFurnitureDetail,btn_next;
    private LinearLayout Linear_furniture,Linear_time,Linear_nation,Linear_furnitureDetail;
    private int FURNITURE_CLICK_CODE = 0;
    private int TIME_CLICK_CODE = 0;
    private int NATION_CLICK_CODE = 0;
    private int FURNITURE_DETAIL_CLICK_CODE = 0;
    private CheckBox cb_furnitureAll,cb_furnitureDesk,cb_furnitureChair,cb_furnitureTable,cb_furnitureSofa,cb_furnitureLamp;
    private CheckBox cb_timeAll;
    private CheckBox cb_nationAll,cb_nationKor,cb_nationUS,cb_nationJp,cb_nationOther;
    private CheckBox cb_furnitureDetailAll;
    private EditText et_year_1,et_year_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_tab);

        btn_slideFurniture = findViewById(R.id.btn_slideFurniture);
        btn_slideTime = findViewById(R.id.btn_slideTime);
        btn_slideNation = findViewById(R.id.btn_slideNation);
        btn_slideFurnitureDetail = findViewById(R.id.btn_slideFurnitureDetail);
        et_year_1 = findViewById(R.id.et_year_1);
        et_year_2 = findViewById(R.id.et_year_2);
        btn_next = findViewById(R.id.btn_next);

        Linear_furniture = findViewById(R.id.Linear_furniture);
        Linear_time = findViewById(R.id.Linear_time);
        Linear_nation = findViewById(R.id.Linear_nation);
        Linear_furnitureDetail = findViewById(R.id.Linear_furnitureDetail);

        cb_furnitureAll = findViewById(R.id.cb_furnitureAll);
        cb_furnitureDesk = findViewById(R.id.cb_furnitureDesk);
        cb_furnitureChair = findViewById(R.id.cb_furnitureChair);
        cb_furnitureTable = findViewById(R.id.cb_furnitureTable);
        cb_furnitureSofa = findViewById(R.id.cb_furnitureSofa);
        cb_furnitureLamp = findViewById(R.id.cb_furnitureLamp);
        cb_timeAll = findViewById(R.id.cb_timeAll);
        cb_nationAll = findViewById(R.id.cb_nationAll);
        cb_nationKor = findViewById(R.id.cb_nationKor);
        cb_nationUS = findViewById(R.id.cb_nationUS);
        cb_nationJp = findViewById(R.id.cb_nationJp);
        cb_nationOther = findViewById(R.id.cb_nationOther);
        cb_furnitureDetailAll = findViewById(R.id.cb_furnitureDetailAll);


        //1. 기본적으로 접혀 있도록 하기 위함
        //2. 각 버튼 클릭시 해당 리니어가 펼쳐질 수 있도록 구현
        //3. 전체 체크박스 클릭시 나머지 체크박스들도 클릭될 수 있도록 구현

        //1.
        if(FURNITURE_CLICK_CODE==0){
        Linear_furniture.setVisibility(View.GONE);
        }
        if (TIME_CLICK_CODE==0){
            Linear_time.setVisibility(View.GONE);
        }
        if (NATION_CLICK_CODE==0){
            Linear_nation.setVisibility(View.GONE);
        }
        if (FURNITURE_DETAIL_CLICK_CODE==0){
            Linear_furnitureDetail.setVisibility(View.GONE);
        }

        //2.
        btn_slideFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FURNITURE_CLICK_CODE==0){
                    Linear_furniture.setVisibility(View.VISIBLE);
                    FURNITURE_CLICK_CODE=1;
                }else if(FURNITURE_CLICK_CODE==1){
                    Linear_furniture.setVisibility(View.GONE);
                    FURNITURE_CLICK_CODE=0;
                }
            }
        });

        btn_slideTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TIME_CLICK_CODE==0){
                    Linear_time.setVisibility(View.VISIBLE);
                    TIME_CLICK_CODE=1;
                }else if(TIME_CLICK_CODE==1){
                    Linear_time.setVisibility(View.GONE);
                    TIME_CLICK_CODE=0;
                }
            }
        });

        btn_slideNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NATION_CLICK_CODE==0){
                    Linear_nation.setVisibility(View.VISIBLE);
                    NATION_CLICK_CODE=1;
                }else if(NATION_CLICK_CODE==1){
                    Linear_nation.setVisibility(View.GONE);
                    NATION_CLICK_CODE=0;
                }
            }
        });

        btn_slideFurnitureDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FURNITURE_DETAIL_CLICK_CODE==0){
                    Linear_furnitureDetail.setVisibility(View.VISIBLE);
                    FURNITURE_DETAIL_CLICK_CODE=1;
                }else if(FURNITURE_DETAIL_CLICK_CODE==1){
                    Linear_furnitureDetail.setVisibility(View.GONE);
                    FURNITURE_DETAIL_CLICK_CODE=0;
                }
            }
        });

        cb_furnitureAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_furnitureAll.isChecked()){
                    cb_furnitureDesk.setChecked(true);
                    cb_furnitureChair.setChecked(true);
                    cb_furnitureTable.setChecked(true);
                    cb_furnitureSofa.setChecked(true);
                    cb_furnitureLamp.setChecked(true);
                }else {
                    cb_furnitureDesk.setChecked(false);
                    cb_furnitureChair.setChecked(false);
                    cb_furnitureTable.setChecked(false);
                    cb_furnitureSofa.setChecked(false);
                    cb_furnitureLamp.setChecked(false);
                }
            }
        });

        cb_timeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cb_timeAll.isChecked()){
                    et_year_1.setVisibility(View.INVISIBLE);
                    et_year_2.setVisibility(View.INVISIBLE);
                }else {
                    et_year_1.setVisibility(View.VISIBLE);
                    et_year_2.setVisibility(View.VISIBLE);
                }
            }
        });

        cb_nationAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_nationAll.isChecked()){
                    cb_nationKor.setChecked(true);
                    cb_nationUS.setChecked(true);
                    cb_nationJp.setChecked(true);
                    cb_nationOther.setChecked(true);
                }else {
                    cb_nationKor.setChecked(false);
                    cb_nationUS.setChecked(false);
                    cb_nationJp.setChecked(false);
                    cb_nationOther.setChecked(false);
                }
            }
        });

        cb_furnitureDetailAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchingTab.this, SearchResult.class);
                startActivity(intent);
            }
        });



    }
}
