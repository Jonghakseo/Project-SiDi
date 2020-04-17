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
import android.widget.Toast;

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
    public static int REVISE_CODE=0;



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

        //1. 메인 화면에서 수정버튼을 클릭시
        //2. 현재 선택된 카테고리 값들을 받아와서
        //3, 체크박스에 체크해주는 코드
        Intent intent = getIntent();
        if (REVISE_CODE==1){
            String existChoice,existChoice2,existChoice3,existChoice4,existChoice5;
            String str_size = intent.getExtras().getString("size");
            if (str_size.equals("1")){
                existChoice = intent.getExtras().getString("existChoice_1");
                existDataCheck(existChoice);

            }else if (str_size.equals("2")){
                existChoice = intent.getExtras().getString("existChoice_1");
                existChoice2 = intent.getExtras().getString("existChoice_2");
                existDataCheck(existChoice);
                existDataCheck(existChoice2);


            }else if (str_size.equals("3")){
                existChoice = intent.getExtras().getString("existChoice_1");
                existChoice2 = intent.getExtras().getString("existChoice_2");
                existChoice3 = intent.getExtras().getString("existChoice_3");
                existDataCheck(existChoice);
                existDataCheck(existChoice2);
                existDataCheck(existChoice3);

            }else if (str_size.equals("4")){
                existChoice = intent.getExtras().getString("existChoice_1");
                existChoice2 = intent.getExtras().getString("existChoice_2");
                existChoice3 = intent.getExtras().getString("existChoice_3");
                existChoice4 = intent.getExtras().getString("existChoice_4");
                existDataCheck(existChoice);
                existDataCheck(existChoice2);
                existDataCheck(existChoice3);
                existDataCheck(existChoice4);


            }else if (str_size.equals("5")){
                existChoice = intent.getExtras().getString("existChoice_1");
                existChoice2 = intent.getExtras().getString("existChoice_2");
                existChoice3 = intent.getExtras().getString("existChoice_3");
                existChoice4 = intent.getExtras().getString("existChoice_4");
                existChoice5 = intent.getExtras().getString("existChoice_5");
                existDataCheck(existChoice);
                existDataCheck(existChoice2);
                existDataCheck(existChoice3);
                existDataCheck(existChoice4);
                existDataCheck(existChoice5);
                cb_all.setChecked(true);
            }
            REVISE_CODE=0;
        }



        //전체 선택시 나머지 체크박스 비활성화
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_all.isChecked()){
                    cb_desk.setChecked(true);
                    cb_chair.setChecked(true);
                    cb_table.setChecked(true);
                    cb_sofa.setChecked(true);
                    cb_lamp.setChecked(true);

                }else {
                    cb_desk.setChecked(false);
                    cb_chair.setChecked(false);
                    cb_table.setChecked(false);
                    cb_sofa.setChecked(false);
                    cb_lamp.setChecked(false);
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

                if (choiceArr.size()!=0){
                Intent intent = new Intent(InitSelectPage.this, MainPageTab.class);
                startActivity(intent);

                finish();
                }else {
                    Toast.makeText(InitSelectPage.this,"카테고리를 선택해주세요",Toast.LENGTH_SHORT).show();
                }





            }
        });




    }

    private void existDataCheck(String tag){
        switch (tag){
            case "#책상":
                cb_desk.setChecked(true);
                break;
            case "#의자":
                cb_chair.setChecked(true);
                break;
            case "#테이블":
                cb_table.setChecked(true);
                break;
            case "#소파":
                cb_sofa.setChecked(true);
                break;
            case "#전등/등":
                cb_lamp.setChecked(true);
                break;

        }
    }

    @Override
    protected void onDestroy() {
//        selectFurnitureHelper.close();
        super.onDestroy();
    }
}
