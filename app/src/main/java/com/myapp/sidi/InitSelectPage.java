package com.myapp.sidi;

import androidx.appcompat.app.AppCompatActivity;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //레이아웃을 보여주는 부분. 첫 방문인지 아닌지는 이 코드 전에 배치해서 불필요한 레이아웃 생성 시간을 아끼도록 한다.
        //
        setContentView(R.layout.activity_init_select_page);

    }

}
