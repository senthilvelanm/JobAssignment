package com.rabobank.exceptionHandler;

import com.rabobank.util.ApplicationConstants;

public class NoDataFoundException extends RuntimeException {

   	private static final long serialVersionUID = -8126316261676861454L;

	public NoDataFoundException() {

        super(ApplicationConstants.DATA_NOT_FOUND);
    }
}
