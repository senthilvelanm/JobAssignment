package com.rabobank.exceptionHandler;

import com.rabobank.util.ApplicationConstants;

public class FileNotFoundException extends Exception{
	
	private static final long serialVersionUID = -924914580284956514L;

	public FileNotFoundException() {

        super(ApplicationConstants.FILE_NOT_FOUND);
    }

}
