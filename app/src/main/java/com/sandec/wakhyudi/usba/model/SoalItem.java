package com.sandec.wakhyudi.usba.model;

import com.google.gson.annotations.SerializedName;


public class SoalItem{

	@SerializedName("a")
	private String answerA;

	@SerializedName("soal")
	private String soal;

	@SerializedName("b")
	private String answerB;

	@SerializedName("c")
	private String answerC;

	@SerializedName("d")
	private String answerD;

	@SerializedName("e")
	private String answerE;

	private String answerN = "none";

	@SerializedName("idGambar")
	private String idGambar;

	@SerializedName("no_soal")
	private int noSoal;


	private int selectedRadioButtonId;
	private int selectedPositionQuestion;
	private String finalAnswer;


	public int getSelectedPositionQuestion() {
		return selectedPositionQuestion;
	}

	public void setSelectedPositionQuestion(int selectedPositionQuestion) {
		this.selectedPositionQuestion = selectedPositionQuestion;
	}

	public int getSelectedRadioButtonId() {
		return selectedRadioButtonId;
	}

	public void setSelectedRadioButtonId(int selectedRadioButtonId) {
		this.selectedRadioButtonId = selectedRadioButtonId;
	}

	public String getFinalAnswer() {
		return finalAnswer;
	}

	public void setFinalAnswer(String finalAnswer) {
		this.finalAnswer = finalAnswer;
	}

	public String getAnswerA() {
		return answerA;
	}

	public String getSoal() {
		return soal;
	}

	public String getAnswerB() {
		return answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public String getAnswerE() {
		return answerE;
	}

	public String getAnswerN() {
		return answerN;
	}

	public String getIdGambar() {
		return idGambar;
	}

	public int getNoSoal() {
		return noSoal;
	}
}