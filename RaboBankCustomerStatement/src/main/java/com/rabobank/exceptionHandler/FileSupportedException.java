package com.rabobank.exceptionHandler;

import com.rabobank.util.ApplicationConstants;

public class FileSupportedException extends Exception{
	
	private static final long serialVersionUID = 5833686602729701530L;

	public FileSupportedException() {

        super(ApplicationConstants.SUPPORTED_FORMAT);
    }

}
