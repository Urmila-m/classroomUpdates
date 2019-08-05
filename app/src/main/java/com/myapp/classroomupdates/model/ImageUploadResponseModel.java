package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class ImageUploadResponseModel implements Serializable {
	private String detail;
	private String url;

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"ImageUploadResponseModel{" + 
			"detail = '" + detail + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}
