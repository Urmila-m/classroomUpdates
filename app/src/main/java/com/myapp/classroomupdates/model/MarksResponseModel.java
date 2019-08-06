package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MarksResponseModel implements Serializable {
	private String year;

	@SerializedName("full_marks")
	private int fullMarks;
	private String subject;
	private String part;
	private String marks;

	@SerializedName("th_pr")
	private String thPr;


	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	public void setFullMarks(int fullMarks){
		this.fullMarks = fullMarks;
	}

	public int getFullMarks(){
		return fullMarks;
	}

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return subject;
	}

	public void setPart(String part){
		this.part = part;
	}

	public String getPart(){
		return part;
	}

	public void setMarks(String marks){
		this.marks = marks;
	}

	public String getMarks(){
		return marks;
	}

	public void setThPr(String thPr){
		this.thPr = thPr;
	}

	public String getThPr(){
		return thPr;
	}

	@Override
 	public String toString(){
		return
			"Response{" +
			"year = '" + year + '\'' +
			",full_marks = '" + fullMarks + '\'' +
			",subject = '" + subject + '\'' +
			",part = '" + part + '\'' +
			",marks = '" + marks + '\'' +
			",th_pr = '" + thPr + '\'' +
			"}";
		}
}
