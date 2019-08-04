package com.myapp.classroomupdates.model;

public class NoticeModel{
	private int notice_by;
	private String year;
	private String part;
	private String batch;
	private boolean send_email;
	private int id;
	private String message;
	private boolean send_sms;
	private int programme;

	public NoticeModel(){

    }

	public NoticeModel(int notice_by, String year, String part, String batch, boolean send_email, int id, String message, boolean send_sms, int programme) {
		this.notice_by = notice_by;
		this.year = year;
		this.part = part;
		this.batch = batch;
		this.send_email = send_email;
		this.id = id;
		this.message = message;
		this.send_sms = send_sms;
		this.programme = programme;
	}

	public void setNoticeBy(int notice_by){
		this.notice_by = notice_by;
	}

	public int getNoticeBy(){
		return notice_by;
	}

	public void setYear(String year){
		this.year = year;
	}

	public String getYear(){
		return year;
	}

	public void setPart(String part){
		this.part = part;
	}

	public String getPart(){
		return part;
	}

	public void setBatch(String batch){
		this.batch = batch;
	}

	public String getBatch(){
		return batch;
	}

	public void setSendEmail(boolean send_email){
		this.send_email = send_email;
	}

	public boolean isSendEmail(){
		return send_email;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setSendSms(boolean send_sms){
		this.send_sms = send_sms;
	}

	public boolean isSendSms(){
		return send_sms;
	}

	public void setProgramme(int programme){
		this.programme = programme;
	}

	public int getProgramme(){
		return programme;
	}

	@Override
 	public String toString(){
		return 
			"NoticeModel{" + 
			"notice_by = '" + notice_by + '\'' + 
			",year = '" + year + '\'' + 
			",part = '" + part + '\'' + 
			",batch = '" + batch + '\'' + 
			",send_email = '" + send_email + '\'' + 
			",id = '" + id + '\'' + 
			",message = '" + message + '\'' + 
			",send_sms = '" + send_sms + '\'' + 
			",programme = '" + programme + '\'' + 
			"}";
		}
}
