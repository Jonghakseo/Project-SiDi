package com.myapp.sidi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapp.sidi.main.MainPageTab;

public class InitSelectPage extends AppCompatActivity {
    /**
     * 이 액티비티에서 해야 할 일
     * <p>
     * 1. 쉐어드에 저장된 방문기록 확인 후, 첫 방문이라면 이 페이지의 레이아웃을 보여준다.
     * 첫 방문이 아니라면 쉐어드에서 가져온 선택 데이터를 메인 페이지에 인텐트와 함께 넘기고 메인 페이지를 시작시킨다.
     * <p>
     * 2. 레이아웃을 보여줬다면, 선택값을 받아서 쉐어드에 저장한다.
     * 3. 기본적으로 시작하기 버튼은 비활성화 상태이고, 하나 이상의 선택이 존재할 경우, 활성화된다.
     * 모든 선택이 해제된다면 다시 비활성화 된다.
     * <p>
     * 4. 전체선택을 누를 경우, 전체선택을 포함한 모든 이미지뷰가 활성화되게 처리해야함.
     * <p>
     * <p>
     * 추후에 로딩화면을 배치한다면, 이 액티비티 이전에 배치해도 된다. 다만 매니페스트 수정이 있어야 함.
     **/

    SharedPreferences visitCheckShared;//방문여부와 기존 카테고리 선택 데이터 확인에 사용할 쉐어드 선언
    SharedPreferences.Editor editor;//사용할 에디터 선언

    final int SELECT_ALL = 11111;
    final int SELECT_DESK = 10000;
    final int SELECT_CHAIR = 1000;
    final int SELECT_TABLE = 100;
    final int SELECT_SOFA = 10;
    final int SELECT_LIGHT = 1;
    final int SELECT_NONE = 0;

    int totalSelect = 0;
    //선택된 카테고리 값을 저장할 int 선언


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        visitCheckShared = getSharedPreferences("visitCheck", MODE_PRIVATE);
        //체크할 쉐어드를 가져옴

        int selectedCategory = visitCheckShared.getInt("selected", SELECT_NONE);
        Log.d("@_SelectedCategoryNumber_@", String.valueOf(selectedCategory));

        if (selectedCategory != SELECT_NONE) {
            /** != 는 테스트 목적으로 체크와 상관없이 현재페이지 노출시에 사용**/
            //visit 쉐어드에서 저장된 카테고리 값 가져옴. 값이 없다면 0(SELECT_NONE) 반환)

        } else {
            //방문 기록이 존재한다면 기존 선택했던 카테고리 정보와 함께 다음 액티비티인 MainPageTab 으로 보낸다.
            Intent intent = new Intent(InitSelectPage.this, MainPageTab.class);
            intent.putExtra("selectedCategory", selectedCategory);
            startActivity(intent);
            finish();

        }

        //레이아웃을 보여주는 부분. 첫 방문인지 아닌지는 이 코드 전에 배치해서 불필요한 레이아웃 생성 시간을 아끼자..
        setContentView(R.layout.activity_init_select_page);


    }


    //이 코드들은 선택 완료 후 다음 페이지로 넘어갈 때 필요. 확실히 골라야 방문으로 인정.

    ImageView allIMG;
    ImageView deskIMG;
    ImageView chairIMG;
    ImageView tableIMG;
    ImageView sofaIMG;
    ImageView lightIMG;
    //각 선택 가능한 이미지뷰 선언
    Button nextButton;
    //메인 페이지로 넘어가는 버튼 선언

    @Override
    protected void onResume() {
        super.onResume();

        allIMG = findViewById(R.id.init_select_all_img);
        deskIMG = findViewById(R.id.init_select_desk_img);
        chairIMG = findViewById(R.id.init_select_chair_img);
        tableIMG = findViewById(R.id.init_select_table_img);
        sofaIMG = findViewById(R.id.init_select_sofa_img);
        lightIMG = findViewById(R.id.init_select_light_img);
        //각 선택 가능한 이미지뷰 선언
        nextButton = findViewById(R.id.init_next_button);
        //메인 페이지로 넘어가는 버튼 선언

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = visitCheckShared.edit();//에디터 선언
                editor.putInt("selected", totalSelect);//이제 첫 방문이 아님. 선택한 값 저장.
                editor.apply();//에디터 변경사항 적용(commit 역할)

                Intent intent = new Intent(InitSelectPage.this, MainPageTab.class);
                intent.putExtra("selectedCategory", totalSelect);
                startActivity(intent);
                finish();
            }
        });//메인 페이지로 가는 버튼

        allIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect < SELECT_ALL) {
                    //현재 선택사항이 전체선택이 아니라면 전체선택 실시.
                    totalSelect = SELECT_ALL;
                } else {
                    //이미 전체선택중
                    totalSelect = SELECT_NONE;
                }
                onSelectChange();//선택에 변경이 있었음
            }
        });//전체선택 이미지뷰

        deskIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect / SELECT_DESK  %10 != 1) {
                    //책상 미선택 되어있던 상태
                    totalSelect += SELECT_DESK;//책상 선택시켜줌
                } else {
                    //이미 선택중
                    totalSelect -= SELECT_DESK;//책상 빼줌
                }
                onSelectChange();
            }
        });//책상 선택 이미지뷰

        chairIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect / SELECT_CHAIR  %10 != 1) {
                    //의자 미선택 되어있던 상태
                    totalSelect += SELECT_CHAIR;//의자 선택시켜줌
                } else {
                    //이미 선택중
                    totalSelect -= SELECT_CHAIR;//의자 빼줌
                }
                onSelectChange();
            }
        });//의자 선택 이미지뷰

        tableIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect / SELECT_TABLE  %10 != 1) {
                    totalSelect += SELECT_TABLE;
                } else {
                    totalSelect -= SELECT_TABLE;
                }
                onSelectChange();
            }
        });//테이블 선택 이미지뷰

        sofaIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect / SELECT_SOFA %10 != 1) {
                    totalSelect += SELECT_SOFA;
                } else {
                    totalSelect -= SELECT_SOFA;
                }
                onSelectChange();
            }
        });//소파 선택 이미지뷰

        lightIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalSelect / SELECT_LIGHT %10 != 1) {
                    totalSelect += SELECT_LIGHT;
                } else {
                    totalSelect -= SELECT_LIGHT;
                }
                onSelectChange();
            }
        });//전등 선택 이미지뷰

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void onSelectChange() {
//        Toast.makeText(InitSelectPage.this, String.valueOf(totalSelect), Toast.LENGTH_SHORT).show();
//        선택값에 대한 테스트 코드

        int filterColor = Color.GRAY;//필터 컬러 설정

        if (totalSelect == SELECT_NONE) {
            //미선택
            nextButton.setEnabled(false);
            //버튼 비활성화
            allIMG.clearColorFilter();
            deskIMG.clearColorFilter();
            chairIMG.clearColorFilter();
            tableIMG.clearColorFilter();
            sofaIMG.clearColorFilter();
            lightIMG.clearColorFilter();
            //모든 필터 제거함
        } else {
            nextButton.setEnabled(true);
            //버튼 활성화
            if (totalSelect == SELECT_ALL) {
                allIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);//색상으로 덮는 필터 적용
                deskIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                chairIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                tableIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                sofaIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                lightIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
            } else {
                allIMG.clearColorFilter();
                if (totalSelect / SELECT_DESK % 10 == 1) {
                    //책상만 선택, 혹은 책상도 선택
                    deskIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                } else {
                    deskIMG.clearColorFilter();
                }
                if (totalSelect / SELECT_CHAIR %10 == 1) {
                    //의자만 선택, 혹은 의자도 선택
                    chairIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                } else {
                    chairIMG.clearColorFilter();
                }
                if (totalSelect / SELECT_TABLE %10 == 1) {
                    //테이블만 선택, 혹은 테이블도 선택
                    tableIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                } else {
                    tableIMG.clearColorFilter();
                }
                if (totalSelect / SELECT_SOFA  %10 == 1) {
                    //소파만 선택, 혹은 소파도 선택
                    sofaIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                } else {
                    sofaIMG.clearColorFilter();
                }
                if (totalSelect / SELECT_LIGHT  %10 == 1) {
                    //전등만 선택, 혹은 전등도 선택
                    lightIMG.setColorFilter(filterColor, PorterDuff.Mode.OVERLAY);
                } else {
                    lightIMG.clearColorFilter();
                }
            }
        }
    }
}
