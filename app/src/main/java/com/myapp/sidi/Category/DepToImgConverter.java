package com.myapp.sidi.Category;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.myapp.sidi.R;

public class DepToImgConverter {


    public void deskConverter_dep_1(String tagName,Button button){
        switch (tagName){
            case "CRT(탑-일자)책상":
                button.setBackgroundResource(R.drawable.dep1_1);
                break;
            case "웨이브 책상":
                button.setBackgroundResource(R.drawable.dep1_2);
                break;
            case "가운데 서랍형(학원&연수용)":
                button.setBackgroundResource(R.drawable.dep1_3);
                break;
            case "컴퓨터 책상":
                button.setBackgroundResource(R.drawable.dep1_4);
                break;
            case "앤틱(장식)형 책상":
                button.setBackgroundResource(R.drawable.dep1_5);
                break;
            case "H책상(책상/책장세트)":
                button.setBackgroundResource(R.drawable.dep1_6);
                break;
            case "복합형":
                button.setBackgroundResource(R.drawable.dep1_7);
                break;
            case "용도불분명":
                button.setBackgroundResource(R.drawable.dep1_8);
                break;
            case "기타":
                button.setBackgroundResource(R.drawable.dep1_9);
            default:
                break;
        }
    }

    public void deskConverter_dep_2(String tagName,Button button){
        switch (tagName){
            case "직사각형(일자형)":
                button.setBackgroundResource(R.drawable.dep2_1);
                break;
            case "웨이브 직선형":
                button.setBackgroundResource(R.drawable.dep2_2);
                break;
            case "웨이브 곡선형":
                button.setBackgroundResource(R.drawable.dep2_3);
                break;
            case "원변형":
                button.setBackgroundResource(R.drawable.dep2_4);
                break;
            case "다각형":
                button.setBackgroundResource(R.drawable.dep2_5);
                break;
            case "복합형":
                button.setBackgroundResource(R.drawable.dep2_6);
                break;
            case "상판이덮여있음(뚜껑)":
                button.setBackgroundResource(R.drawable.dep2_7);
                break;
            case "기타":
                button.setBackgroundResource(R.drawable.dep2_8);
                break;
            default:
                break;
        }
    }

    public void deskConverter_dep_3(String tagName,Button button){
        String result="";
        switch (tagName){
            case "일자기둥형":
                button.setBackgroundResource(R.drawable.dep3_1);
                break;
            case "측판형":
                button.setBackgroundResource(R.drawable.dep3_2);
                break;
            case "통형":
                button.setBackgroundResource(R.drawable.dep3_3);
                break;
            case "역 Y/T자 형":
                button.setBackgroundResource(R.drawable.dep3_4);
                break;
            case "ㄴ/ㄷ 형":
                button.setBackgroundResource(R.drawable.dep3_5);
                break;
            case "Z/X자 형":
                button.setBackgroundResource(R.drawable.dep3_6);
                break;
            case "복합형":
                button.setBackgroundResource(R.drawable.dep3_7);
                break;
            case "다리없음":
                button.setBackgroundResource(R.drawable.dep3_8);
                break;
            case "기타":
                button.setBackgroundResource(R.drawable.dep3_9);
                break;
            default:
                break;
        }
    }

    public void deskConverter_dep_4(String tagName,Button button){
        String result="";
        switch (tagName){
            case "캐스터(바퀴)":
                button.setBackgroundResource(R.drawable.dep4_1);
                break;
            case "높이 조절형":
                button.setBackgroundResource(R.drawable.dep4_2);
                break;
            case "복합형":
                button.setBackgroundResource(R.drawable.dep4_3);
                break;
            case "다리 밑 장치 없음":
                button.setBackgroundResource(R.drawable.dep4_4);
                break;
            case "기타":
                button.setBackgroundResource(R.drawable.dep4_5);
                break;
            default:
                break;
        }
    }

    public void deskConverter_dep_5(String tagName,Button button){
        String result="";
        switch (tagName){
            case "편수형(한쪽 서랍)":
                button.setBackgroundResource(R.drawable.dep5_1);
                break;
            case "양수형(양쪽서랍)":
                button.setBackgroundResource(R.drawable.dep5_2);
                break;
            case "중앙 (선반)형":
                button.setBackgroundResource(R.drawable.dep5_3);
                break;
            case "복합형":
                button.setBackgroundResource(R.drawable.dep5_4);
                break;
            case "서랍없음":
                button.setBackgroundResource(R.drawable.dep5_5);
                break;
            case "기타":
                button.setBackgroundResource(R.drawable.dep5_6);
                break;
            default:
                break;
        }
    }



}
