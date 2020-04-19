package com.myapp.sidi.ContractAndHelper;
import android.provider.BaseColumns;
public class SelectFurnitureContract {
    private SelectFurnitureContract(){}
    public static class TableEntry implements BaseColumns{
        public static final String TABLE_NAME ="SelectFurniture";
        public static final String DELETE_KEY = "DeleteKey";
        public static final String COLUMN_CHOICE_1="Choice1";
        public static final String COLUMN_CHOICE_2 ="Choice2";
        public static final String COLUMN_CHOICE_3 ="Choice3";
        public static final String COLUMN_CHOICE_4 ="Choice4";
        public static final String COLUMN_CHOICE_5 ="Choice5";
        public static final String COLUMN_CHOICE_6 ="Choice6";
    }
}