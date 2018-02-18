package com.sandec.wakhyudi.usba.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{
	@SerializedName("soal")
	private List<SoalItem> listSoal;

	public void setListSoal(List<SoalItem> listSoal){
		this.listSoal = listSoal;
	}

	public List<SoalItem> getListSoal(){
		return listSoal;
	}

}