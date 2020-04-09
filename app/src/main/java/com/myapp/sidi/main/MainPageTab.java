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
    Button btn_choice;
    LinearLayout linearLayout_1;
    String choice_1,choice_2,choice_3,choice_4,choice_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_tab);
//        btn_choice= findViewById(R.id.btn_choice);
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

        List choiceArr = new ArrayList<>();
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



//        for(int i=0; i<item.size(); i++){
//            Log.e("itemElement", String.valueOf(item.get(i)));
//        }


        linearLayout_1 = findViewById(R.id.linearLayout_1);

        for(int i=0; i<choiceArr.size(); i++) {
            Button button = new Button(this);
            button.setText(choiceArr.get(i).toString());
            linearLayout_1.addView(button);
        }

    }
}
