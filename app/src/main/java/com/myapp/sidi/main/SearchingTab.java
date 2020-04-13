package com.myapp.sidi.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.myapp.sidi.Category.ChairInfo;
import com.myapp.sidi.Category.DeskInfo;
import com.myapp.sidi.Category.LampDispatchInfo;
import com.myapp.sidi.Category.LampHangInfo;
import com.myapp.sidi.Category.SofaInfo;
import com.myapp.sidi.Category.TableInfo;
import com.myapp.sidi.R;

public class SearchingTab extends AppCompatActivity {
    private LinearLayout Linear_furniture,Linear_time,Linear_nation,Linear_furnitureDetail;
    private int CATEGORY_CLICK_CODE = 0;
    private int TIME_CLICK_CODE = 0;
    private int NATION_CLICK_CODE = 0;
    private int FURNITURE_FORM_CLICK_CODE = 0;
    private int FIRST_VISIT_CODE_FURNITURE_FORM = 0;
    private int DEP_1_CLICK_CODE=0;
    private int DEP_2_CLICK_CODE=0;
    private int DEP_3_CLICK_CODE=0;
    private int DEP_4_CLICK_CODE=0;
    private int DEP_5_CLICK_CODE=0;
    private int form_count;
    private RadioButton rb_furnitureDesk,rb_furnitureChair,rb_furnitureTable,rb_furnitureSofa,rb_furnitureDispatchLamp,rb_furnitureHangLamp;
    private RadioGroup rg_furniture;
    private CheckBox cb_timeAll;
    private CheckBox cb_nationAll,cb_nationKor,cb_nationUS,cb_nationJp,cb_nationOther;
    private CheckBox cb_furnitureDetailAll;
    private EditText et_year_1,et_year_2;
    private Button btn_dep_1, btn_dep_2, btn_dep_3, btn_dep_4, btn_dep_5;
    private Button btn_FurnitureCategory,btn_FurnitureTime, btn_FurnitureForm,btn_FurnitureNation;
    private LinearLayout Linear_furnitureDetailTotal,Linear_dep_1,Linear_dep_2,Linear_dep_3,Linear_dep_4,Linear_dep_5;
    private CheckBox searchFormCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_tab);

        et_year_1 = findViewById(R.id.et_year_1);
        et_year_2 = findViewById(R.id.et_year_2);

        Linear_furniture = findViewById(R.id.Linear_furniture);
        Linear_time = findViewById(R.id.Linear_time);
        Linear_nation = findViewById(R.id.Linear_nation);
        Linear_furnitureDetailTotal = findViewById(R.id.Linear_furnitureDetailTotal);
//        Linear_furnitureDetail = findViewById(R.id.Linear_furnitureDetail);

        rb_furnitureDesk = findViewById(R.id.rb_furnitureDesk);
        rb_furnitureChair = findViewById(R.id.rb_furnitureChair);
        rb_furnitureTable = findViewById(R.id.rb_furnitureTable);
        rb_furnitureSofa = findViewById(R.id.rb_furnitureSofa);
        rb_furnitureDispatchLamp = findViewById(R.id.rb_furnitureDispatchLamp);
        rb_furnitureHangLamp = findViewById(R.id.rb_furnitureHangLamp);
        rg_furniture = findViewById(R.id.rg_furniture);

        cb_timeAll = findViewById(R.id.cb_timeAll);
        cb_nationAll = findViewById(R.id.cb_nationAll);
        cb_nationKor = findViewById(R.id.cb_nationKor);
        cb_nationUS = findViewById(R.id.cb_nationUS);
        cb_nationJp = findViewById(R.id.cb_nationJp);
        cb_nationOther = findViewById(R.id.cb_nationOther);
//        cb_furnitureDetailAll = findViewById(R.id.cb_furnitureDetailAll);

        btn_dep_1 = findViewById(R.id.btn_dep_1);
        btn_dep_2 = findViewById(R.id.btn_dep_2);
        btn_dep_3 = findViewById(R.id.btn_dep_3);
        btn_dep_4 = findViewById(R.id.btn_dep_4);
        btn_dep_5 = findViewById(R.id.btn_dep_5);
        btn_FurnitureCategory = findViewById(R.id.btn_FurnitureCategory);
        btn_FurnitureTime = findViewById(R.id.btn_FurnitureTime);
        btn_FurnitureNation = findViewById(R.id.btn_FurnitureNation);
        btn_FurnitureForm = findViewById(R.id.btn_FurnitureForm);


        Linear_dep_1 = findViewById(R.id.Linear_dep_1);
        Linear_dep_2 = findViewById(R.id.Linear_dep_2);
        Linear_dep_3 = findViewById(R.id.Linear_dep_3);
        Linear_dep_4 = findViewById(R.id.Linear_dep_4);
        Linear_dep_5 = findViewById(R.id.Linear_dep_5);


        //형태 분류 초기값은 안보이게 설정
        btn_dep_1.setVisibility(View.GONE);
        btn_dep_2.setVisibility(View.GONE);
        btn_dep_3.setVisibility(View.GONE);
        btn_dep_4.setVisibility(View.GONE);
        btn_dep_5.setVisibility(View.GONE);
        Linear_dep_1.setVisibility(View.GONE);
        Linear_dep_2.setVisibility(View.GONE);
        Linear_dep_3.setVisibility(View.GONE);
        Linear_dep_4.setVisibility(View.GONE);
        Linear_dep_5.setVisibility(View.GONE);

        btn_dep_1.setBackground(ContextCompat.getDrawable(this,R.drawable.blue_round_line));
        btn_dep_2.setBackground(ContextCompat.getDrawable(this,R.drawable.blue_round_line));
        btn_dep_3.setBackground(ContextCompat.getDrawable(this,R.drawable.blue_round_line));
        btn_dep_4.setBackground(ContextCompat.getDrawable(this,R.drawable.blue_round_line));
        btn_dep_5.setBackground(ContextCompat.getDrawable(this,R.drawable.blue_round_line));

        btn_dep_1.setTextColor(Color.WHITE);
        btn_dep_2.setTextColor(Color.WHITE);
        btn_dep_3.setTextColor(Color.WHITE);
        btn_dep_4.setTextColor(Color.WHITE);
        btn_dep_5.setTextColor(Color.WHITE);

        btn_dep_1.setTextSize(10);
        btn_dep_2.setTextSize(10);
        btn_dep_3.setTextSize(10);
        btn_dep_4.setTextSize(10);
        btn_dep_5.setTextSize(10);

        btn_dep_1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,70));
        btn_dep_2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,70));
        btn_dep_3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,70));
        btn_dep_4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,70));
        btn_dep_5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,70));

    }

    @Override
    protected void onResume() {
        super.onResume();







            rg_furniture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int rbID) {

            //가구를 선택했으니 형태 검색 버튼이 나오도록 하기
            FIRST_VISIT_CODE_FURNITURE_FORM=1;
            btn_FurnitureForm.setVisibility(View.VISIBLE);
            form_count=0;

                //해당 값들 초기화
                btn_dep_1.setText(null);
                btn_dep_2.setText(null);
                btn_dep_3.setText(null);
                btn_dep_4.setText(null);
                btn_dep_5.setText(null);

                Linear_dep_1.removeAllViews();
                Linear_dep_2.removeAllViews();
                Linear_dep_3.removeAllViews();
                Linear_dep_4.removeAllViews();
                Linear_dep_5.removeAllViews();

                //1, 각 라디오 버튼을 클릭 했을 시 아이디 값을 해당 버튼의 텍스트 값으로 변경
                RadioButton radioButton = findViewById(rbID);
                String rb_text = radioButton.getText().toString();

                switch (rb_text){
                    case "책상":
                        DeskInfo deskInfo = new DeskInfo();
                        form_count = deskInfo.dep_count;
                        //depth 체크
                        depthCheck(form_count);

                        //책상의 형태 유형 타이틀 지정
                        btn_dep_1.setText(deskInfo.desk_dep1);
                        btn_dep_2.setText(deskInfo.desk_dep2);
                        btn_dep_3.setText(deskInfo.desk_dep3);
                        btn_dep_4.setText(deskInfo.desk_dep4);
                        btn_dep_5.setText(deskInfo.desk_dep5);

                        //형태를 선택해야하는 유형들의 depth별로 체크박스 동적으로 만들어주기
                        String[] desk_dep1_searchForm = deskInfo.desk_dep1_searchForm.split(",");
                        for (int i =0; i<desk_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(desk_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] desk_dep2_searchForm = deskInfo.desk_dep2_searchForm.split(",");
                        for (int i =0; i<desk_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(desk_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] desk_dep3_searchForm = deskInfo.desk_dep3_searchForm.split(",");
                        for (int i =0; i<desk_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(desk_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] desk_dep4_searchForm = deskInfo.desk_dep4_searchForm.split(",");
                        for (int i =0; i<desk_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(desk_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }


                        String[] desk_dep5_searchForm = deskInfo.desk_dep5_searchForm.split(",");
                        for (int i =0; i<desk_dep5_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(desk_dep5_searchForm[i]);
                            Linear_dep_5.addView(searchFormCheckBox);
                        }

                        break;

                    case "의자":


                        ChairInfo chairInfo = new ChairInfo();
                        form_count =chairInfo.dep_count;
                        //depth 체크
                        depthCheck(form_count);


                        //의자의 형태 제목
                        btn_dep_1.setText(chairInfo.chair_dep1);
                        btn_dep_2.setText(chairInfo.chair_dep2);
                        btn_dep_3.setText(chairInfo.chair_dep3);
                        btn_dep_4.setText(chairInfo.chair_dep4);


                        //dep별 체크박스 생성
                        String[] chair_dep1_searchForm = chairInfo.chair_dep1_searchForm.split(",");
                        for (int i =0; i<chair_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(chair_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] chair_dep2_searchForm = chairInfo.chair_dep2_searchForm.split(",");
                        for (int i =0; i<chair_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(chair_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] chair_dep3_searchForm = chairInfo.chair_dep3_searchForm.split(",");
                        for (int i =0; i<chair_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(chair_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] chair_dep4_searchForm = chairInfo.chair_dep4_searchForm.split(",");
                        for (int i =0; i<chair_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(chair_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }
                        break;

                    case "테이블":
                        TableInfo tableInfo = new TableInfo();
                        form_count = tableInfo.dep_count;

                        //depth 체크
                        depthCheck(form_count);


                        //테이블의 형태 제목
                        btn_dep_1.setText(tableInfo.table_dep1);
                        btn_dep_2.setText(tableInfo.table_dep2);
                        btn_dep_3.setText(tableInfo.table_dep3);
                        btn_dep_4.setText(tableInfo.table_dep4);
                        btn_dep_5.setText(tableInfo.table_dep5);

                        //dep별 체크박스 생성
                        String[] table_dep1_searchForm = tableInfo.table_dep1_searchForm.split(",");
                        for (int i =0; i<table_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(table_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] table_dep2_searchForm = tableInfo.table_dep2_searchForm.split(",");
                        for (int i =0; i<table_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(table_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] table_dep3_searchForm = tableInfo.table_dep3_searchForm.split(",");
                        for (int i =0; i<table_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(table_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] table_dep4_searchForm = tableInfo.table_dep4_searchForm.split(",");
                        for (int i =0; i<table_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(table_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }

                        String[] table_dep5_searchForm = tableInfo.table_dep5_searchForm.split(",");
                        for (int i =0; i<table_dep5_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(table_dep5_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }

                        break;


                    case "소파":

                        SofaInfo sofaInfo = new SofaInfo();
                        form_count = sofaInfo.dep_count;
                        depthCheck(form_count);

                        btn_dep_1.setText(sofaInfo.sofa_dep1);
                        btn_dep_2.setText(sofaInfo.sofa_dep2);
                        btn_dep_3.setText(sofaInfo.sofa_dep3);
                        btn_dep_4.setText(sofaInfo.sofa_dep4);
                        btn_dep_5.setText(sofaInfo.sofa_dep5);

                        //dep별 체크박스 생성
                        String[] sofa_dep1_searchForm = sofaInfo.sofa_dep1_searchForm.split(",");
                        for (int i =0; i<sofa_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(sofa_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] sofa_dep2_searchForm = sofaInfo.sofa_dep2_searchForm.split(",");
                        for (int i =0; i<sofa_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(sofa_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] sofa_dep3_searchForm = sofaInfo.sofa_dep3_searchForm.split(",");
                        for (int i =0; i<sofa_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(sofa_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] sofa_dep4_searchForm = sofaInfo.sofa_dep4_searchForm.split(",");
                        for (int i =0; i<sofa_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(sofa_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }

                        String[] sofa_dep5_searchForm = sofaInfo.sofa_dep5_searchForm.split(",");
                        for (int i =0; i<sofa_dep5_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(sofa_dep5_searchForm[i]);
                            Linear_dep_5.addView(searchFormCheckBox);
                        }
                        break;

                    case "전등(벽에 부착식)":

                        LampDispatchInfo lampDispatchInfo = new LampDispatchInfo();
                        form_count = lampDispatchInfo.dep_count;
                        depthCheck(form_count);

                        //테이블의 형태 제목
                        btn_dep_1.setText(lampDispatchInfo.lampDispatch_dep1);
                        btn_dep_2.setText(lampDispatchInfo.lampDispatch_dep2);
                        btn_dep_3.setText(lampDispatchInfo.lampDispatch_dep3);
                        btn_dep_4.setText(lampDispatchInfo.lampDispatch_dep4);

                        //dep별 체크박스 생성
                        String[] lampDispatch_dep1_searchForm = lampDispatchInfo.lampDispatch_dep1_searchForm.split(",");
                        for (int i =0; i<lampDispatch_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampDispatch_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] lampDispatch_dep2_searchForm = lampDispatchInfo.lampDispatch_dep2_searchForm.split(",");
                        for (int i =0; i<lampDispatch_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampDispatch_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] lampDispatch_dep3_searchForm = lampDispatchInfo.lampDispatch_dep3_searchForm.split(",");
                        for (int i =0; i<lampDispatch_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampDispatch_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] lampDispatch_dep4_searchForm = lampDispatchInfo.lampDispatch_dep4_searchForm.split(",");
                        for (int i =0; i<lampDispatch_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampDispatch_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }
                        break;

                    case "전등(벽에 매다는 식)":
                        LampHangInfo lampHangInfo = new LampHangInfo();
                        form_count = lampHangInfo.dep_count;
                        depthCheck(form_count);

                        //테이블의 형태 제목
                        btn_dep_1.setText(lampHangInfo.lampHang_dep1);
                        btn_dep_2.setText(lampHangInfo.lampHang_dep2);
                        btn_dep_3.setText(lampHangInfo.lampHang_dep3);
                        btn_dep_4.setText(lampHangInfo.lampHang_dep4);

                        //dep별 체크박스 생성
                        String[] lampHang_dep1_searchForm = lampHangInfo.lampHang_dep1_searchForm.split(",");
                        for (int i =0; i<lampHang_dep1_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampHang_dep1_searchForm[i]);
                            Linear_dep_1.addView(searchFormCheckBox);
                        }

                        String[] lampHang_dep2_searchForm = lampHangInfo.lampHang_dep2_searchForm.split(",");
                        for (int i =0; i<lampHang_dep2_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampHang_dep2_searchForm[i]);
                            Linear_dep_2.addView(searchFormCheckBox);
                        }

                        String[] lampHang_dep3_searchForm = lampHangInfo.lampHang_dep3_searchForm.split(",");
                        for (int i =0; i<lampHang_dep3_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampHang_dep3_searchForm[i]);
                            Linear_dep_3.addView(searchFormCheckBox);
                        }

                        String[] lampHang_dep4_searchForm = lampHangInfo.lampHang_dep4_searchForm.split(",");
                        for (int i =0; i<lampHang_dep4_searchForm.length; i++){
                            searchFormCheckBox = new CheckBox(getApplicationContext());
                            searchFormCheckBox.setText(lampHang_dep4_searchForm[i]);
                            Linear_dep_4.addView(searchFormCheckBox);
                        }
                        break;


                    default:
                        Toast.makeText(SearchingTab.this, "ERROR-형태를 불러올 수 없습니다.",Toast.LENGTH_SHORT).show();
                        break;
                }
                Log.e("rg_id", rb_text);

            }
        });








        //1. 기본적으로 접혀 있도록 하기 위함
        //2. 각 버튼 클릭시 해당 리니어가 펼쳐질 수 있도록 구현
        //3. 전체 체크박스 클릭시 나머지 체크박스들도 클릭될 수 있도록 구현

        //1.
        if(CATEGORY_CLICK_CODE ==0){
        Linear_furniture.setVisibility(View.GONE);
        }
        if (TIME_CLICK_CODE==0){
            Linear_time.setVisibility(View.GONE);
        }
        if (NATION_CLICK_CODE==0){
            Linear_nation.setVisibility(View.GONE);
        }







        //가구 카테고리 접엇다 폇쳣다 기능 구현
        btn_FurnitureCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CATEGORY_CLICK_CODE==0){
                    Linear_furniture.setVisibility(View.VISIBLE);
                    CATEGORY_CLICK_CODE=1;
                }else if(CATEGORY_CLICK_CODE==1){
                    Linear_furniture.setVisibility(View.GONE);
                    CATEGORY_CLICK_CODE=0;
                }
            }
        });

        //연도 선택 접었다 펼쳤다 기능 구현
        btn_FurnitureTime.setOnClickListener(new View.OnClickListener() {
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

        //국가 선택 접었다 펼쳤다 기능 구현
        btn_FurnitureNation.setOnClickListener(new View.OnClickListener() {
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



        //가구 카테고리를 선택하지 않았을 경우 GONE
        //가구 카테고리를 선택하는 코드쪽에서 VISIBLE
        if (FIRST_VISIT_CODE_FURNITURE_FORM==0){
            btn_FurnitureForm.setVisibility(View.GONE);
        }





            //상세 형태 검색 클릭시
            //유형별 제목만 보이도록 함
            btn_FurnitureForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //가구 카테고리를 선택하고 난 뒤에 형태 검색이 보이도록 하기 위한 예외처리
                    //하나라도 클릭했을 경우부터 보이도록 함

                        //형태 검색 버튼을 클릭했을 경우 펼치기
                        if (FURNITURE_FORM_CLICK_CODE == 0) {

                            btn_dep_1.setVisibility(View.VISIBLE);
                            btn_dep_2.setVisibility(View.VISIBLE);
                            btn_dep_3.setVisibility(View.VISIBLE);
                            btn_dep_4.setVisibility(View.VISIBLE);
                            btn_dep_5.setVisibility(View.VISIBLE);
                            //dep_4인경우 예외처리 부분
                            Log.e("form_count", String.valueOf(form_count));
                            if (form_count == 4) {
                                btn_dep_5.setVisibility(View.GONE);
                            }
                            FURNITURE_FORM_CLICK_CODE = 1;
                            //한번더 클릭했을 경우 닫기
                        } else if (FURNITURE_FORM_CLICK_CODE == 1) {
                            btn_dep_1.setVisibility(View.GONE);
                            btn_dep_2.setVisibility(View.GONE);
                            btn_dep_3.setVisibility(View.GONE);
                            btn_dep_4.setVisibility(View.GONE);
                            btn_dep_5.setVisibility(View.GONE);
                            Linear_dep_1.setVisibility(View.GONE);
                            Linear_dep_2.setVisibility(View.GONE);
                            Linear_dep_3.setVisibility(View.GONE);
                            Linear_dep_4.setVisibility(View.GONE);
                            Linear_dep_5.setVisibility(View.GONE);
                            FURNITURE_FORM_CLICK_CODE = 0;
                        }

                }
            });



        //유형별 제목 클릭시
        //해당 유형의 상세 내용들 보여준다.

        btn_dep_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEP_1_CLICK_CODE==0){
                Linear_dep_1.setVisibility(View.VISIBLE);
                DEP_1_CLICK_CODE=1;
                }else if(DEP_1_CLICK_CODE==1){
                Linear_dep_1.setVisibility(View.GONE);
                DEP_1_CLICK_CODE=0;
                }
            }
        });

        btn_dep_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEP_2_CLICK_CODE==0){
                    Linear_dep_2.setVisibility(View.VISIBLE);
                    DEP_2_CLICK_CODE=1;
                }else if(DEP_2_CLICK_CODE==1){
                    Linear_dep_2.setVisibility(View.GONE);
                    DEP_2_CLICK_CODE=0;
                }
            }
        });

        btn_dep_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEP_3_CLICK_CODE==0){
                    Linear_dep_3.setVisibility(View.VISIBLE);
                    DEP_3_CLICK_CODE=1;
                }else if(DEP_3_CLICK_CODE==1){
                    Linear_dep_3.setVisibility(View.GONE);
                    DEP_3_CLICK_CODE=0;
                }
            }
        });

        btn_dep_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEP_4_CLICK_CODE==0){
                    Linear_dep_4.setVisibility(View.VISIBLE);
                    DEP_4_CLICK_CODE=1;
                }else if(DEP_4_CLICK_CODE==1){
                    Linear_dep_4.setVisibility(View.GONE);
                    DEP_4_CLICK_CODE=0;
                }
            }
        });
        btn_dep_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DEP_5_CLICK_CODE==0){
                    Linear_dep_5.setVisibility(View.VISIBLE);
                    DEP_5_CLICK_CODE=1;
                }else if(DEP_5_CLICK_CODE==1){
                    Linear_dep_5.setVisibility(View.GONE);
                    DEP_5_CLICK_CODE=0;
                }
            }
        });




        //체크박스 전체 선택시 동작하는 부분
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




    }
    public void depthCheck(int count){
        switch (count){
            case 5:
                break;
            case 4:
                btn_dep_5.setVisibility(View.GONE);
                Linear_dep_5.setVisibility(View.GONE);
                break;
            case 3:
                btn_dep_5.setVisibility(View.GONE);
                Linear_dep_5.setVisibility(View.GONE);
                btn_dep_4.setVisibility(View.GONE);
                Linear_dep_4.setVisibility(View.GONE);
                break;
            case 2:
                btn_dep_5.setVisibility(View.GONE);
                Linear_dep_5.setVisibility(View.GONE);
                btn_dep_4.setVisibility(View.GONE);
                Linear_dep_4.setVisibility(View.GONE);
                btn_dep_3.setVisibility(View.GONE);
                Linear_dep_3.setVisibility(View.GONE);
                break;
            case 1:
                btn_dep_5.setVisibility(View.GONE);
                Linear_dep_5.setVisibility(View.GONE);
                btn_dep_4.setVisibility(View.GONE);
                Linear_dep_4.setVisibility(View.GONE);
                btn_dep_3.setVisibility(View.GONE);
                Linear_dep_3.setVisibility(View.GONE);
                btn_dep_2.setVisibility(View.GONE);
                Linear_dep_2.setVisibility(View.GONE);

                break;
            default:
                Toast.makeText(SearchingTab.this,"error",Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
