package com.sandec.wakhyudi.usba.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wakhyudi on 18/02/18.
 */

public class JawabanItem {
    List<SoalItem>listSoal;
    List<String>listJawaban;


    public JawabanItem(List<SoalItem> listSoal) {
        this.listSoal = listSoal;
    }

//    public List<String> getListJawaban() {
//        for (int i = 0; i <listSoal.size() ; i++) {
//            listJawaban = new ArrayList<>();
//            listJawaban.add(listSoal.get(i).getAnswerA());
//        }
//
//        return listSoal;
//    }
}
