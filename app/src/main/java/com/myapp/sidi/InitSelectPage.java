package com.myapp.sidi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.myapp.sidi.ContractAndHelper.SelectFurnitureContract;
import com.myapp.sidi.ContractAndHelper.SelectFurnitureHelper;
import com.myapp.sidi.main.MainPageTab;

import java.util.ArrayList;

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

    CheckBox cb_desk,cb_chair,cb_table,cb_sofa,cb_lamp,cb_all;
    Button btn_next;
    String choice_1,choice_2,choice_3,choice_4,choice_5;
    String deleteKey="deleteKey";
    SelectFurnitureHelper selectFurnitureHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_select_page);

        cb_desk = findViewById(R.id.cb_desk);
        cb_chair = findViewById(R.id.cb_chair);
        cb_table = findViewById(R.id.cb_table);
        cb_sofa = findViewById(R.id.cb_sofa);
        cb_lamp = findViewById(R.id.cb_lamp);
        cb_all = findViewById(R.id.cb_all);
        btn_next = findViewById(R.id.btn_next);


        //전체 선택시 나머지 체크박스 비활성화
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_all.isChecked()){
                    cb_desk.setChecked(false);
                    cb_chair.setChecked(false);
                    cb_table.setChecked(false);
                    cb_sofa.setChecked(false);
                    cb_lamp.setChecked(false);

                    cb_desk.setEnabled(false);
                    cb_chair.setEnabled(false);
                    cb_table.setEnabled(false);
                    cb_sofa.setEnabled(false);
                    cb_lamp.setEnabled(false);
                }else {
                    cb_desk.setEnabled(true);
                    cb_chair.setEnabled(true);
                    cb_table.setEnabled(true);
                    cb_sofa.setEnabled(true);
                    cb_lamp.setEnabled(true);
                }
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFurnitureHelper = new SelectFurnitureHelper(getApplicationContext());
                SQLiteDatabase sqLiteDatabase = selectFurnitureHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                ArrayList choiceArr = new ArrayList();

                //1. 이전에 선택하였던 가구 카테고리를 지우는 부분
                String DELETE_PREVIOUS_DATA= SelectFurnitureContract.TableEntry.DELETE_KEY+" LIKE ?";
                String[] WHERE = {"deleteKey"};
                int DELETED_ROWS = sqLiteDatabase.delete(SelectFurnitureContract.TableEntry.TABLE_NAME,DELETE_PREVIOUS_DATA,WHERE);
                Log.e("deletedRow", String.valueOf(DELETED_ROWS));

                //1.체크박스를 선택한 값들을 어레이에 담는다.
                //2.어레이에 담긴 값들을 꺼내서 sqLite에 저장시킨다.
                if(cb_desk.isChecked()){
                    choiceArr.add("#책상");
                }else{

                }
                if(cb_chair.isChecked()){
                    choiceArr.add("#의자");
                }else {

                }
                if(cb_table.isChecked()){
                    choiceArr.add("#테이블");
                }else {

                }
                if(cb_sofa.isChecked()){
                    choiceArr.add("#소파");
                }else {

                }
                if(cb_lamp.isChecked()){
                    choiceArr.add("#전등/등");
                }else {

                }
                if(cb_all.isChecked()){
                    choiceArr.add("#전체선택");
                }else {

                }

                //데이터 값 확인용
//                for(int i=0; i<choiceArr.size(); i++){
//                    Log.e("test",choiceArr.get(i).toString());
//                }

                int count = choiceArr.size();
                switch (count){
                    case 1:
                        choice_1=choiceArr.get(0).toString();
                        choice_2="";
                        choice_3="";
                        choice_4="";
                        choice_5="";
                        break;
                    case 2:
                        choice_1=choiceArr.get(0).toString();
                        choice_2=choiceArr.get(1).toString();
                        choice_3="";
                        choice_4="";
                        choice_5="";
                        break;
                    case 3:
                        choice_1=choiceArr.get(0).toString();
                        choice_2=choiceArr.get(1).toString();
                        choice_3=choiceArr.get(2).toString();
                        choice_4="";
                        choice_5="";
                        break;
                    case 4:
                        choice_1=choiceArr.get(0).toString();
                        choice_2=choiceArr.get(1).toString();
                        choice_3=choiceArr.get(2).toString();
                        choice_4=choiceArr.get(3).toString();
                        choice_5="";
                        break;
                    case 5:
                        choice_1=choiceArr.get(0).toString();
                        choice_2=choiceArr.get(1).toString();
                        choice_3=choiceArr.get(2).toString();
                        choice_4=choiceArr.get(3).toString();
                        choice_5=choiceArr.get(4).toString();
                        break;
                    default:
                        break;
                }


                contentValues.put(SelectFurnitureContract.TableEntry.DELETE_KEY,deleteKey);
                contentValues.put(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1,choice_1);
                contentValues.put(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2,choice_2);
                contentValues.put(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3,choice_3);
                contentValues.put(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4,choice_4);
                contentValues.put(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5,choice_5);

                //1. 추가된 row의 id(PRIMARY KEY)값을 반환한다.
                long rowId = sqLiteDatabase.insert(SelectFurnitureContract.TableEntry.TABLE_NAME,null,contentValues);
                Log.e("rowId", String.valueOf(rowId));

                Intent intent = new Intent(InitSelectPage.this, MainPageTab.class);
                startActivity(intent);

            }
        });




    }

    @Override
    protected void onDestroy() {
        selectFurnitureHelper.close();
        super.onDestroy();
    }
}
