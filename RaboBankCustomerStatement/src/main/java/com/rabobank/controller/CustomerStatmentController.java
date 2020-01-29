package com.rabobank.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rabobank.exceptionHandler.FileNotFoundException;
import com.rabobank.exceptionHandler.FileSupportedException;
import com.rabobank.model.ApplicationResponseInfo;
import com.rabobank.model.CustomerStatment;
import com.rabobank.service.ReadRecordsAndValidation;
import com.rabobank.util.ApplicationConstants;

@RestController
@RequestMapping("/rabobank")
public class CustomerStatmentController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerStatmentController.class);
	
	@Autowired
	private ReadRecordsAndValidation readRecordsAndValidationImpl;

	@RequestMapping("")
	public String assignment() {
		
		return "Rabobank Customer Statement Processor";
		
	}
	
	@RequestMapping(value = "/customerStatment", method = RequestMethod.POST)
	public @ResponseBody ApplicationResponseInfo handleFileUpload(@RequestParam("file") MultipartFile multipart) throws Exception {

		ApplicationResponseInfo appResponse = new ApplicationResponseInfo();
		
		logger.info("handleFileUpload method is calling for statmentProcessor endpoint ");

		if (!multipart.isEmpty()) {

			if (multipart.getContentType().equalsIgnoreCase(ApplicationConstants.FILE_TYPE_CSV)) {
				
				logger.info(" Starting CSV file process ");

				List<CustomerStatment> errorRecords = new ArrayList<CustomerStatment>();

				File csvFile = new File(multipart.getOriginalFilename());

				multipart.transferTo(csvFile);

				List<CustomerStatment> extractedRecords = readRecordsAndValidationImpl.readRecordsFromCSV(csvFile);

				if(extractedRecords.size()==0) {
					
					//throw new NoDataFoundException();
					 appResponse.setResponseCode(ApplicationConstants.HTTP_NO_CONTENT);		        
				      appResponse.setResponseMessage(ApplicationConstants.DATA_NOT_FOUND);
				      return appResponse;
				      
					
				}
				errorRecords.addAll(readRecordsAndValidationImpl.findDuplicateRecords(extractedRecords));

				errorRecords.addAll(readRecordsAndValidationImpl.findEndBalanceErrorRecords(extractedRecords));

				if (!errorRecords.isEmpty()) {

					appResponse.setResponseCode(ApplicationConstants.HTTP_CODE_SUCCESS);

					appResponse.setResponseMessage(ApplicationConstants.VALIDATION_ERROR);

					appResponse.setRecords(errorRecords);

				} else {

					appResponse.setResponseCode(ApplicationConstants.HTTP_CODE_SUCCESS);

					appResponse.setResponseMessage(ApplicationConstants.VALIDATION_ERROR);

				}
				logger.info(" Ending CSV file process ");

			} else if (multipart.getContentType().equalsIgnoreCase(ApplicationConstants.FILE_TYPE_XML)) {

				logger.info(" Starting XML file process ");
				
				List<CustomerStatment> errorRecords = new ArrayList<CustomerStatment>();

				File xmlFile = new File(multipart.getOriginalFilename());

				multipart.transferTo(xmlFile);

				List<CustomerStatment> extractedRecords = readRecordsAndValidationImpl.readRecordsFromXML(xmlFile);

				if(extractedRecords.size()==0) {
					
					//throw new NoDataFoundException();
					 appResponse.setResponseCode(ApplicationConstants.HTTP_NO_CONTENT);		        
				      appResponse.setResponseMessage(ApplicationConstants.DATA_NOT_FOUND);
				      return appResponse;
				      
					
				}
				
				errorRecords.addAll(readRecordsAndValidationImpl.findDuplicateRecords(extractedRecords));

				errorRecords.addAll(readRecordsAndValidationImpl.findEndBalanceErrorRecords(extractedRecords));

				if (!errorRecords.isEmpty()) {

					appResponse.setResponseCode(ApplicationConstants.HTTP_CODE_SUCCESS);

					appResponse.setResponseMessage(ApplicationConstants.VALIDATION_ERROR);

					appResponse.setRecords(errorRecords);

				} else {

					appResponse.setResponseCode(ApplicationConstants.HTTP_CODE_SUCCESS);

					appResponse.setResponseMessage(ApplicationConstants.VALIDATION_ERROR);

				}
				logger.info(" Ending XML file process ");
			} else {
				
				logger.info(" Supporting CSV and XML format files only");

				throw new FileSupportedException();

			}

		} else {

			
			throw new FileNotFoundException();

		}

		return appResponse;

	}

	
}
