package com.rabobank.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ApplicationResponseInfo {

	private String responseMessage;

	private int responseCode;

	private List<CustomerStatment> Records;



	public String getResponseMessage() {

		return responseMessage;

	}



	public void setResponseMessage(String responseMessage) {

		this.responseMessage = responseMessage;

	}



	public int getResponseCode() {

		return responseCode;

	}



	public void setResponseCode(int responseCode) {

		this.responseCode = responseCode;

	}



	@Override

	public String toString() {

		return "Error [responseMessage=" + responseMessage + ", responseCode=" + responseCode + "]";

	}



	public List<CustomerStatment> getRecords() {

		return Records;

	}



	public void setRecords(List<CustomerStatment> records) {

		Records = records;

	}

}