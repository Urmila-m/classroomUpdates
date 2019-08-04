package com.myapp.classroomupdates.model;

public class AttendClassRequestModel {
	private String date;
	private int routine_detail;
	private String remark;
	private boolean is_attending;
	private String to_time;
	private boolean send_sms;
	private String from_time;

	public AttendClassRequestModel(int routine_detail, String date, boolean is_attending, String from_time, String to_time, String remark, boolean send_sms) {
		this.date = date;
		this.routine_detail = routine_detail;
		this.remark = remark;
		this.is_attending = is_attending;
		this.to_time = to_time;
		this.send_sms = send_sms;
		this.from_time = from_time;
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

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setIsAttending(boolean is_attending){
		this.is_attending = is_attending;
	}

	public boolean isIsAttending(){
		return is_attending;
	}

	public void setToTime(String to_time){
		this.to_time = to_time;
	}

	public String getToTime(){
		return to_time;
	}

	public void setSendSms(boolean send_sms){
		this.send_sms = send_sms;
	}

	public boolean isSendSms(){
		return send_sms;
	}

	public void setFromTime(String from_time){
		this.from_time = from_time;
	}

	public String getFromTime(){
		return from_time;
	}

	@Override
 	public String toString(){
		return 
			"AttendClassModel{" + 
			"date = '" + date + '\'' + 
			",routine_detail = '" + routine_detail + '\'' + 
			",remark = '" + remark + '\'' + 
			",is_attending = '" + is_attending + '\'' + 
			",to_time = '" + to_time + '\'' + 
			",send_sms = '" + send_sms + '\'' + 
			",from_time = '" + from_time + '\'' + 
			"}";
		}
}
