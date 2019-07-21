package com.myapp.classroomupdates.model;

import com.google.gson.annotations.SerializedName;

public class ClassResponseModel{

	@SerializedName("date")
	private String date;

	@SerializedName("remark")
	private String remark;

	@SerializedName("is_attending")
	private boolean isAttending;

	@SerializedName("is_permanent")
	private boolean isPermanent;

	@SerializedName("from_time")
	private String fromTime;

	@SerializedName("notify")
	private boolean notify;

	@SerializedName("routine_detail")
	private int routineDetail;

	@SerializedName("datetime_update")
	private String datetimeUpdate;

	@SerializedName("teacher")
	private int teacher;

	@SerializedName("datetime_created")
	private String datetimeCreated;

	@SerializedName("id")
	private int id;

	@SerializedName("to_time")
	private String toTime;

	@SerializedName("send_sms")
	private boolean sendSms;

	@SerializedName("is_cancelled")
	private boolean isCancelled;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
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

	public void setIsPermanent(boolean isPermanent){
		this.isPermanent = isPermanent;
	}

	public boolean isIsPermanent(){
		return isPermanent;
	}

	public void setFromTime(String fromTime){
		this.fromTime = fromTime;
	}

	public String getFromTime(){
		return fromTime;
	}

	public void setNotify(boolean notify){
		this.notify = notify;
	}

	public boolean isNotify(){
		return notify;
	}

	public void setRoutineDetail(int routineDetail){
		this.routineDetail = routineDetail;
	}

	public int getRoutineDetail(){
		return routineDetail;
	}

	public void setDatetimeUpdate(String datetimeUpdate){
		this.datetimeUpdate = datetimeUpdate;
	}

	public String getDatetimeUpdate(){
		return datetimeUpdate;
	}

	public void setTeacher(int teacher){
		this.teacher = teacher;
	}

	public int getTeacher(){
		return teacher;
	}

	public void setDatetimeCreated(String datetimeCreated){
		this.datetimeCreated = datetimeCreated;
	}

	public String getDatetimeCreated(){
		return datetimeCreated;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
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

	public void setIsCancelled(boolean isCancelled){
		this.isCancelled = isCancelled;
	}

	public boolean isIsCancelled(){
		return isCancelled;
	}

	@Override
 	public String toString(){
		return 
			"ClassResponseModel{" + 
			"date = '" + date + '\'' + 
			",remark = '" + remark + '\'' + 
			",is_attending = '" + isAttending + '\'' + 
			",is_permanent = '" + isPermanent + '\'' + 
			",from_time = '" + fromTime + '\'' + 
			",notify = '" + notify + '\'' + 
			",routine_detail = '" + routineDetail + '\'' + 
			",datetime_update = '" + datetimeUpdate + '\'' + 
			",teacher = '" + teacher + '\'' + 
			",datetime_created = '" + datetimeCreated + '\'' + 
			",id = '" + id + '\'' + 
			",to_time = '" + toTime + '\'' + 
			",send_sms = '" + sendSms + '\'' + 
			",is_cancelled = '" + isCancelled + '\'' + 
			"}";
		}
}