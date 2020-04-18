package com.myapp.sidi.Category;

import android.util.Log;

import com.google.gson.internal.$Gson$Preconditions;

public class NameToNumConverter {

    public String deskConverter_dep_1(String name){
        switch (name){
            case "CRT(탑 일자)책상":
                name = "1";
                return name;
            case "웨이브 책상":
                name = "2";
                return name;
            case "가운데 서랍형(학원&연수용)":
                name = "3";
                return name;
            case "컴퓨터 책상":
                name = "4";
                return name;
            case "앤틱(장식)형 책상":
                name = "5";
                return name;
            case "H책상(책상/책장세트)":
                name = "6";
                return name;
            case "복합형":
                name = "7";
                return name;
            case "용도불분명":
                name = "8";
                return name;
            case "기타":
                name = "9";
                return name;
            default:
                break;
        }
        return name;
    }

    public String deskConverter_dep_2(String name){
        String result="";
        switch (name){
            case "직사각형(일자형)":
                result = "1";
                break;
            case "웨이브 직선형":
                result = "2";
                break;
            case "웨이브 곡선형":
                result = "3";
                break;
            case "원변형":
                result = "4";
                break;
            case "다각형":
                result = "5";
                break;
            case "복합형":
                result = "6";
                break;
            case "상판이덮여있음(뚜껑)":
                result = "7";
                break;
            case "기타":
                result = "8";
                break;
            default:
                break;
        }
        return result;
    }

    public String deskConverter_dep_3(String name){
        String result="";
        switch (name){
            case "일자기둥형":
                result = "1";
                break;
            case "측판형":
                result = "2";
                break;
            case "통형":
                result = "3";
                break;
            case "역 Y/T자 형":
                result = "4";
                break;
            case "ㄴ/ㄷ 형":
                result = "5";
                break;
            case "Z/X자 형":
                result = "6";
                break;
            case "복합형":
                result = "7";
                break;
            case "다리없음":
                result = "8";
                break;
            case "기타":
                result = "9";
                break;
            default:
                break;
        }
        return result;
    }

    public String deskConverter_dep_4(String name){
        String result="";
        switch (name){
            case "캐스터(바퀴)":
                result = "1";
                break;
            case "높이 조절형":
                result = "2";
                break;
            case "복합형":
                result = "3";
                break;
            case "다리 밑 장치 없음":
                result = "4";
                break;
            case "기타":
                result = "5";
                break;
            default:
                break;
        }
        return result;
    }

    public String deskConverter_dep_5(String name){
        String result="";
        switch (name){
            case "편수형(한쪽 서랍)":
                result = "1";
                break;
            case "양수형(양쪽서랍)":
                result = "2";
                break;
            case "중앙 (선반)형":
                result = "3";
                break;
            case "복합형":
                result = "4";
                break;
            case "서랍없음":
                result = "5";
                break;
            case "기타":
                result = "6";
                break;
            default:
                break;
        }
        return result;
    }

    public String chairConverter_dep_1(String name){
        String result="";
        switch (name){
            case "스툴":
                result = "1";
                break;
            case "소의자":
                result = "2";
                break;
            case "팔걸이의자":
                result = "3";
                break;
            case "바체어":
                result = "4";
                break;
            case "좌식의자":
                result = "5";
                break;
            case "흔들의자":
                result = "6";
                break;
            case "안락의자":
                result = "7";
                break;
            case "접이식의자":
                result = "8";
                break;
            case "유아용의자":
                result = "9";
                break;
            case "사무용의자":
                result = "10";
                break;
            case "특수의자":
                result = "11";
                break;
            case "기타 종류":
                result = "12";
                break;
            default:
                break;
        }
        return result;
    }

    public String chairConverter_dep_2(String name){
        String result="";
        switch (name){
            case "사각형":
                result = "1";
                break;
            case "둥근사각형":
                result = "2";
                break;
            case "사각변형형":
                result = "3";
                break;
            case "그외 각형":
                result = "4";
                break;
            case "원형":
                result = "5";
                break;
            case "원변형형":
                result = "6";
                break;
            case "자유형":
                result = "7";
                break;
            case "등받이 없음":
                result = "8";
                break;
            case "기타":
                result = "9";
                break;
            default:
                break;
        }
        return result;
    }

    public String chairConverter_dep_3(String name){
        String result="";
        switch (name){
            case "직선형":
                result = "1";
                break;
            case "곡선형":
                result = "2";
                break;
            case "직선+곡선형":
                result = "3";
                break;
            case "교차형":
                result = "4";
                break;
            case "방사형":
                result = "5";
                break;
            case "받침형":
                result = "6";
                break;
            case "통형":
                result = "7";
                break;
            case "가로대직선형":
                result = "8";
                break;
            case "가로대곡선형":
                result = "9";
                break;
            case "가로대 직선+곡선형":
                result = "10";
                break;
            case "판형":
                result = "11";
                break;
            case "다리 없음":
                result = "12";
                break;
            case "기타 다리 형상":
                result = "13";
                break;
            default:
                break;
        }
        return result;
    }

    public String chairConverter_dep_4(String name){
        String result="";
        switch (name){
            case "민무늬":
                result = "1";
                break;
            case "표면무늬":
                result = "2";
                break;
            case "가로 부재":
                result = "3";
                break;
            case "세로 부재":
                result = "4";
                break;
            case "교차/사선부재":
                result = "5";
                break;
            case "내부 장식":
                result = "6";
                break;
            case "뚫림":
                result = "7";
                break;
            case "기타":
                result = "8";
                break;
            default:
                break;
        }
        return result;
    }


    public String tableConverter_dep_1(String name){
        String result="";
        switch (name){
            case "직사각형":
                result = "1";
                break;
            case "정사각형":
                result = "2";
                break;
            case "사각 변형":
                result = "3";
                break;
            case "정원형":
                result = "4";
                break;
            case "타원형":
                result = "5";
                break;
            case "다각형":
                result = "6";
                break;
            case "복합형":
                result = "7";
                break;
            case "복합 변형":
                result = "8";
                break;
            case "기타":
                result = "9";
                break;
            default:
                break;
        }
        return result;
    }

    public String tableConverter_dep_2(String name){
        String result="";
        switch (name){
            case "장식적 상판":
                result = "1";
                break;
            case "장식적 다리":
                result = "2";
                break;
            case "복합(상판+다리)":
                result = "3";
                break;
            case "해당없음":
                result = "4";
                break;
            default:
                break;
        }
        return result;
    }

    public String tableConverter_dep_3(String name){
        String result="";
        switch (name){
            case "다른물품 유":
                result = "1";
                break;
            case "해당없음":
                result = "2";
                break;
            case "기타":
                result = "8";
                break;
            default:
                break;
        }
        return result;
    }

    public String tableConverter_dep_4(String name){
        String result="";
        switch (name){
            case "수납장 유":
                result = "1";
                break;
            case "해당없음":
                result = "2";
                break;
            default:
                break;
        }
        return result;
    }

    public String tableConverter_dep_5(String name){
        String result="";
        switch (name){
            case "일자형":
                result = "1";
                break;
            case "일체형":
                result = "2";
                break;
            case "역Y자형":
                result = "3";
                break;
            case "X자형":
                result = "4";
                break;
            case "판형":
                result = "5";
                break;
            case "통 형":
                result = "6";
                break;
            case "사물형":
                result = "7";
                break;
            case "역T형":
                result = "8";
                break;
            case "복합형":
                result = "9";
                break;
            case "연결형":
                result = "10";
                break;
            case "기타":
                result = "";
                break;
            default:
                break;
        }
        return result;
    }


    public String sofaConverter_dep_1(String name){
        String result="";
        switch (name){
            case "일자형":
                result = "1";
                break;
            case "ㄱ자형":
                result = "2";
                break;
            case "곡선형":
                result = "3";
                break;
            case "양면/사방형":
                result = "4";
                break;
            case "기타":
                result = "5";
                break;
            default:
                break;
        }
        return result;
    }

    public String sofaConverter_dep_2(String name){
        String result="";
        switch (name){
            case "1개 분할":
                result = "1";
                break;
            case "2개 분할":
                result = "2";
                break;
            case "3개 분할":
                result = "3";
                break;
            case "4개이상 분할":
                result = "4";
                break;
            case "좌판분할없음":
                result = "5";
                break;
            case "기타":
                result = "6";
                break;
            default:
                break;
        }
        return result;
    }

    public String sofaConverter_dep_3(String name){
        String result="";
        switch (name){
            case "정사각형":
                result = "1";
                break;
            case "둥근형":
                result = "2";
                break;
            case "사각복합형":
                result = "3";
                break;
            case "직사각형":
                result = "4";
                break;
            case "기타":
                result = "5";
                break;
            default:
                break;
        }
        return result;
    }

    public String sofaConverter_dep_4(String name){
        String result="";
        switch (name){
            case "사각형":
                result = "1";
                break;
            case "둥근형":
                result = "2";
                break;
            case "장식형":
                result = "3";
                break;
            case "머리쿠션 포함형":
                result = "4";
                break;
            case "둥근 사각형":
                result = "5";
                break;
            case "사각변형형":
                result = "6";
                break;
            case "등받이 없음":
                result = "7";
                break;
            case "기타":
                result = "8";
                break;
            default:
                break;
        }
        return result;
    }

    public String sofaConverter_dep_5(String name){
        String result="";
        switch (name){
            case "양방향":
                result = "1";
                break;
            case "우형(오른쪽)":
                result = "2";
                break;
            case "좌형(왼쪽)":
                result = "3";
                break;
            case "팔걸이없음":
                result = "4";
                break;
            case "기타":
                result = "5";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampDispatchConverter_dep_1(String name){
        String result="";
        switch (name){
            case "삼각기둥":
                result = "1";
                break;
            case "사각기둥":
                result = "2";
                break;
            case "다각기둥":
                result = "3";
                break;
            case "원기둥":
                result = "4";
                break;
            case "각뿔형":
                result = "5";
                break;
            case "원뿔형":
                result = "6";
                break;
            case "구형":
                result = "7";
                break;
            case "판형":
                result = "8";
                break;
            case "사물형":
                result = "9";
                break;
            case "기타":
                result = "10";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampDispatchConverter_dep_2(String name){
        String result="";
        switch (name){
            case "기본형":
                result = "1";
                break;
            case "변형":
                result = "2";
                break;
            case "절단형":
                result = "3";
                break;
            case "절단변형":
                result = "4";
                break;
            case "장형":
                result = "5";
                break;
            case "복합":
                result = "6";
                break;
            case "인공물":
                result = "7";
                break;
            case "자연물":
                result = "8";
                break;
            case "다중복합":
                result = "9";
                break;
            case "기타 세부형상":
                result = "10";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampDispatchConverter_dep_3(String name){
        String result="";
        switch (name){
            case "부분무늬":
                result = "1";
                break;
            case "전체무늬":
                result = "2";
                break;
            case "무늬없음":
                result = "3";
                break;
            case "기타":
                result = "4";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampDispatchConverter_dep_4(String name){
        String result="";
        switch (name){
            case "구/반구":
                result = "1";
                break;
            case "원기둥/원뿔":
                result = "2";
                break;
            case "각기둥/각뿔":
                result = "3";
                break;
            case "변형":
                result = "4";
                break;
            case "줄/고리연결(천정)":
                result = "5";
                break;
            case "연결부 없음":
                result = "6";
                break;
            case "기타 연결부":
                result = "7";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampHangConverter_dep_1(String name){
        String result="";
        switch (name){
            case "삼각기둥":
                result = "1";
                break;
            case "사각기둥":
                result = "2";
                break;
            case "다각기둥":
                result = "3";
                break;
            case "원기둥":
                result = "4";
                break;
            case "각뿔형":
                result = "5";
                break;
            case "원뿔형":
                result = "6";
                break;
            case "구형/반구형":
                result = "7";
                break;
            case "판재형":
                result = "8";
                break;
            case "구상형":
                result = "9";
                break;
            case "기타 형상":
                result = "10";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampHangConverter_dep_2(String name){
        String result="";
        switch (name){
            case "기본형":
                result = "1";
                break;
            case "변형":
                result = "2";
                break;
            case "절단형":
                result = "3";
                break;
            case "절단변형":
                result = "4";
                break;
            case "장형":
                result = "5";
                break;
            case "복합":
                result = "6";
                break;
            case "인공물":
                result = "7";
                break;
            case "자연물":
                result = "8";
                break;
            case "다중복합":
                result = "9";
                break;
            case "기타 세부형상":
                result = "10";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampHangConverter_dep_3(String name){
        String result="";
        switch (name){
            case "부분무늬":
                result = "1";
                break;
            case "전체무늬":
                result = "2";
                break;
            case "무늬없음":
                result = "3";
                break;
            case "기타 무늬":
                result = "4";
                break;
            default:
                break;
        }
        return result;
    }

    public String lampHangConverter_dep_4(String name){
        String result="";
        switch (name){
            case "구/반구":
                result = "1";
                break;
            case "원기둥/원뿔":
                result = "2";
                break;
            case "각기둥/각뿔":
                result = "3";
                break;
            case "1줄":
                result = "4";
                break;
            case "2/3줄":
                result = "5";
                break;
            case "4줄이상":
                result = "6";
                break;
            case "고리연결":
                result = "7";
                break;

            case "기타 연결부":
                result = "8";
                break;
            default:
                break;
        }
        return result;
    }




}
