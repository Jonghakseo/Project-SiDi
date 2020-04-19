package com.myapp.sidi.ContractAndHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.myapp.sidi.ContractAndHelper.SelectFurnitureContract;

public class SelectFurnitureHelper extends SQLiteOpenHelper {
    public static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",    //DB에 들어갈 테이블 형태
                    SelectFurnitureContract.TableEntry.TABLE_NAME,
                    SelectFurnitureContract.TableEntry._ID,
                    SelectFurnitureContract.TableEntry.DELETE_KEY,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_1,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_2,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_3,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_4,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_5,
                    SelectFurnitureContract.TableEntry.COLUMN_CHOICE_6);

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SelectFurnitureContract.TableEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 10;

    public static final String DATABASE_NAME = "TeamProject.db";

    public SelectFurnitureHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}