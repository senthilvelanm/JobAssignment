package com.rabobank.service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabobank.exceptionHandler.NoDataFoundException;
import com.rabobank.model.CustomerStatementRecords;
import com.rabobank.model.CustomerStatment;

@Service
public class ReadRecordsAndValidation {
	
		
	private static final Logger logger = LoggerFactory.getLogger(ReadRecordsAndValidation.class);
	
	public List<CustomerStatment> readRecordsFromCSV(File file) throws Exception {
		
		logger.info(" readRecordsFromCSV method is processing ");

		HeaderColumnNameTranslateMappingStrategy<CustomerStatment> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<CustomerStatment>();

		beanStrategy.setType(CustomerStatment.class);



		Map<String, String> columnMapping = new HashMap<String, String>();

		columnMapping.put("Reference", "transactionRef");

		columnMapping.put("AccountNumber", "accountNumber");

		columnMapping.put("Description", "description");

		columnMapping.put("Start Balance", "startBalance");

		columnMapping.put("Mutation", "mutation");

		columnMapping.put("End Balance", "endBalance");



		beanStrategy.setColumnMapping(columnMapping);



		CsvToBean<CustomerStatment> csvToBean = new CsvToBean<CustomerStatment>();

		CSVReader reader = new CSVReader(new FileReader(file));

		List<CustomerStatment> records = csvToBean.parse(beanStrategy, reader);
		
		logger.info(" End of readRecordsFromCSV method ");
		
		return records;

	}
	
	public List<CustomerStatment> readRecordsFromXML(File file) throws Exception {

		  
		logger.info(" readRecordsFromXML method is processing "); 
		
	  		
        JAXBContext jaxbContext = JAXBContext.newInstance(CustomerStatementRecords.class);
   
       
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
        
        CustomerStatementRecords rootRecord= (CustomerStatementRecords) jaxbUnmarshaller.unmarshal(file);  

             
        logger.info(" End of readRecordsFromXML method ");

		return rootRecord.getRecord();

	}
	
	
	public List<CustomerStatment> findDuplicateRecords(List<CustomerStatment> records) {
		
		logger.info(" findDuplicateRecords method is processing ");

		Map<Integer, CustomerStatment> uniqeRecords = new HashMap<Integer, CustomerStatment>();

		List<CustomerStatment> duplicateRecords = new ArrayList<CustomerStatment>();

		for (CustomerStatment record : records) {

			if (uniqeRecords.containsKey(record.getTransactionRef())) {

				duplicateRecords.add(record);

			} else {

				uniqeRecords.put(record.getTransactionRef(), record);

			}

		}

		List<CustomerStatment> finalDuplicateRecords = new ArrayList<CustomerStatment>();

		finalDuplicateRecords.addAll(duplicateRecords);

		for (CustomerStatment record : duplicateRecords) {

			if (null != uniqeRecords.get(record.getTransactionRef())) {

				finalDuplicateRecords.add(uniqeRecords.get(record.getTransactionRef()));

				uniqeRecords.remove(record.getTransactionRef());

			}

		}
		 logger.info(" End of findDuplicateRecords method ");
		 logger.info("finalDuplicateRecords.size(--->)"+finalDuplicateRecords.size());
		return finalDuplicateRecords;

	}



	/**

	 * @return List<Records> if startbalance - mutation != endbalance then

	 *         endbalance is wrong that list ll be returned.

	 */

	public List<CustomerStatment> findEndBalanceErrorRecords(List<CustomerStatment> records) {

		logger.info(" findEndBalanceErrorRecords method is processing ");
		List<CustomerStatment> endBalanceErrorRecords = new ArrayList<CustomerStatment>();

		for (CustomerStatment record : records) {

						
			if(Math.round(record.getStartBalance()+record.getMutation()) != Math.round(record.getEndBalance())) {
				endBalanceErrorRecords.add(record);

			}

		}

		logger.info(" End of findEndBalanceErrorRecords method ");
		logger.info("endBalanceErrorRecords.size(--->)"+endBalanceErrorRecords.size());
		return endBalanceErrorRecords;

	}

}
