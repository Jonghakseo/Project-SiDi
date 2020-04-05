package com.myapp.sidi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class InitSelectPage extends AppCompatActivity {
/**
* 이 액티비티에서 해야 할 일
*
* 1. 쉐어드에 저장된 방문기록 확인 후, 첫 방문이라면 이 페이지의 레이아웃을 보여준다.
*       첫 방문이 아니라면 쉐어드에서 가져온 선택 데이터를 메인 페이지에 인텐트와 함께 넘기고 메인 페이지를 시작시킨다.
*
* 2. 레이아웃을 보여줬다면, 선택값을 받아서 쉐어드에 저장한다.
* 3. 기본적으로 시작하기 버튼은 비활성화 상태이고, 하나 이상의 선택이 존재할 경우, 활성화된다.
*   모든 선택이 해제된다면 다시 비활성화 된다.
*
* 4. 전체선택을 누를 경우, 전체선택을 포함한 모든 이미지뷰가 활성화되게 처리해야함.
*
*
 *  추후에 로딩화면을 배치한다면, 이 액티비티 이전에 배치해도 된다. 다만 매니페스트 수정이 있어야 함.
**/

    SharedPreferences visitCheckShared;//방문여부와 기존 카테고리 선택 데이터 확인에 사용할 쉐어드 선언
    SharedPreferences.Editor editor;//사용할 에디터 선언

    final int SELECT_TABLE = 777777;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        visitCheckShared = getSharedPreferences("visitCheck",MODE_PRIVATE);//체크할 쉐어드 가져옴
        if (visitCheckShared.getInt("selected",0)!=0){
            //visit 쉐어드에서 저장된 카테고리 값 가져옴. 값이 없다면 0 반환)

            //레이아웃을 보여주는 부분. 첫 방문인지 아닌지는 이 코드 전에 배치해서 불필요한 레이아웃 생성 시간을 아끼자.
            setContentView(R.layout.activity_init_select_page);


        }else {
            //방문 기록이 존재한다면 기존 선택했던 카테고리 정보와 함께 다음 액티비티인 MainPageTab 으로 보낸다.

        }




    }

//    editor = visitCheckShared.edit();//에디터 선언
//    editor.putBoolean("isFirstVisit",false);//이제 첫 방문이 아니니까 false 로 저장
//    editor.apply();//에디터 변경사항 적용(commit 역할)

    //이 코드들은 선택 완료 후 다음 페이지로 넘어갈 때 필요. 확실히 골라야 방문으로 인정.

}
