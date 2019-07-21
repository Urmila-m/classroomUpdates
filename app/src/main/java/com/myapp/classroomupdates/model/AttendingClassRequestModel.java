package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

public class AttendingClassRequestModel{

	@SerializedName("date")
	private String date;

	@SerializedName("routine_detail")
	private int routineDetail;

	@SerializedName("remark")
	private String remark;

	@SerializedName("is_attending")
	private boolean isAttending;

	@SerializedName("to_time")
	private String toTime;

	@SerializedName("send_sms")
	private boolean sendSms;

	@SerializedName("from_time")
	private String fromTime;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setRoutineDetail(int routineDetail){
		this.routineDetail = routineDetail;
	}

	public int getRoutineDetail(){
		return routineDetail;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}

	public void setIsAttending(boolean isAttending){
		this.isAttending = isAttending;
	}

	public boolean isIsAttending(){
		return isAttending;
	}

	public void setToTime(String toTime){
		this.toTime = toTime;
	}

	public String getToTime(){
		return toTime;
	}

	public void setSendSms(boolean sendSms){
		this.sendSms = sendSms;
	}

	public boolean isSendSms(){
		return sendSms;
	}

	public void setFromTime(String fromTime){
		this.fromTime = fromTime;
	}

	public String getFromTime(){
		return fromTime;
	}

	@Override
 	public String toString(){
		return 
			"AttendingClassRequestModel{" + 
			"date = '" + date + '\'' + 
			",routine_detail = '" + routineDetail + '\'' + 
			",remark = '" + remark + '\'' + 
			",is_attending = '" + isAttending + '\'' + 
			",to_time = '" + toTime + '\'' + 
			",send_sms = '" + sendSms + '\'' + 
			",from_time = '" + fromTime + '\'' + 
			"}";
		}
}