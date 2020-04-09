package com.myapp.sidi.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.myapp.sidi.ContractAndHelper.SelectFurnitureContract;
import com.myapp.sidi.ContractAndHelper.SelectFurnitureHelper;
import com.myapp.sidi.InitSelectPage;
import com.myapp.sidi.R;

import java.util.ArrayList;
import java.util.List;

public class MainPageTab extends AppCompatActivity {
    Button btn_choice,btn_choiceRevise;
    LinearLayout linearLayout_1;
    String choice_1,choice_2,choice_3,choice_4,choice_5;
    String existChoice_1,existChoice_2,existChoice_3,existChoice_4,existChoice_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_tab);
//        btn_choice= findViewById(R.id.btn_choice);

        btn_choiceRevise = findViewById(R.id.btn_choiceRevise);


        SelectFurnitureHelper helper = new SelectFurnitureHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        String[] readData = {
                BaseColumns._ID,
                SelectFurnitureContract.TableEntry.DELETE_KEY,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4,
                SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5
        };
        String selection = SelectFurnitureContract.TableEntry._ID + " = ?";
        String[] selectionArgs = { "1" };
        String sortOrder = SelectFurnitureContract.TableEntry._ID + " DESC";


        Cursor cursor = sqLiteDatabase.query(
                SelectFurnitureContract.TableEntry.TABLE_NAME,
                readData,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        final List choiceArr = new ArrayList<>();
        while(cursor.moveToNext()) {
            choice_1 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1));
            choice_2 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2));
            choice_3 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3));
            choice_4 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4));
            choice_5 = cursor.getString(cursor.getColumnIndexOrThrow(SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5));

            if (!choice_1.isEmpty()){
                choiceArr.add(choice_1);
            }
            if (!choice_2.isEmpty()){
                choiceArr.add(choice_2);
            }
            if (!choice_3.isEmpty()){
                choiceArr.add(choice_3);
            }
            if (!choice_4.isEmpty()){
                choiceArr.add(choice_4);
            }
            if (!choice_5.isEmpty()){
                choiceArr.add(choice_5);
            }
        }
        cursor.close();


        //1. 카테고리 수만큼 버튼 생성하는 코드
        linearLayout_1 = findViewById(R.id.linearLayout_1);
        for(int i=0; i<choiceArr.size(); i++) {
            Button button = new Button(this);
            button.setText(choiceArr.get(i).toString());
            linearLayout_1.addView(button);
        }



        btn_choiceRevise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count=choiceArr.size();
                Log.e("count", String.valueOf(choiceArr.size()));

                switch (count){
                    case 1:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent.putExtra("size","1");
                        intent.putExtra("existChoice_1",choiceArr.get(0).toString());
                        startActivity(intent);

                        break;
                    case 2:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent2 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent2.putExtra("size","2");
                        intent2.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent2.putExtra("existChoice_2",choiceArr.get(1).toString());
                        startActivity(intent2);
                        break;
                    case 3:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent3 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent3.putExtra("size","3");
                        intent3.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent3.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent3.putExtra("existChoice_3",choiceArr.get(2).toString());
                        startActivity(intent3);
                        break;
                    case 4:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent4 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent4.putExtra("size","4");
                        intent4.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent4.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent4.putExtra("existChoice_3",choiceArr.get(2).toString());
                        intent4.putExtra("existChoice_4",choiceArr.get(3).toString());
                        startActivity(intent4);
                        break;
                    case 5:
                        InitSelectPage.REVISE_CODE=1;
                        Intent intent5 = new Intent(MainPageTab.this,InitSelectPage.class);
                        intent5.putExtra("size","5");
                        intent5.putExtra("existChoice_1",choiceArr.get(0).toString());
                        intent5.putExtra("existChoice_2",choiceArr.get(1).toString());
                        intent5.putExtra("existChoice_3",choiceArr.get(2).toString());
                        intent5.putExtra("existChoice_4",choiceArr.get(3).toString());
                        intent5.putExtra("existChoice_5",choiceArr.get(4).toString());
                        startActivity(intent5);
                        break;
                    default:
                        break;
                }



            }
        });
    }
}
