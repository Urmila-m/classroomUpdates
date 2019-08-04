package com.myapp.classroomupdates.model;

public class SetArrivalTimeModel{
	private String date;
	private int routine_detail;
	private String arrival_time;
	private int id;

	public SetArrivalTimeModel(int routine_detail, String date, String arrival_time) {
		this.date = date;
		this.routine_detail = routine_detail;
		this.arrival_time = arrival_time;
	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setRoutineDetail(int routine_detail){
		this.routine_detail = routine_detail;
	}

	public int getRoutineDetail(){
		return routine_detail;
	}

	public void setArrivalTime(String arrival_time){
		this.arrival_time = arrival_time;
	}

	public String getArrivalTime(){
		return arrival_time;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"SetArrivalTimeModel{" + 
			"date = '" + date + '\'' + 
			",routine_detail = '" + routine_detail + '\'' + 
			",arrival_time = '" + arrival_time + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}
