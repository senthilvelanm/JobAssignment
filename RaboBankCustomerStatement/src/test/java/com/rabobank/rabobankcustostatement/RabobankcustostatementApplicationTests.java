package com.rabobank.rabobankcustostatement;


import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rabobank.model.CustomerStatment;
import com.rabobank.service.ReadRecordsAndValidation;
import com.rabobank.util.RecordsCount;

import junit.framework.Assert;

@SpringBootTest
public class RabobankcustostatementApplicationTests {


		/**

		  * Positive case checking
		 */

		@Test
		public void getDuplicateRecordsTestCaseWithDuplilcate() {

			List<CustomerStatment> inputList = Arrays.asList(

					new CustomerStatment(12345, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),

					new CustomerStatment(12345, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			List<CustomerStatment> duplicateRecords = readRecordsAndValidation.findDuplicateRecords(inputList);

			assertEquals(inputList.size(), duplicateRecords.size());



		}



		/**

		 * Negative case checking

		 */

		@Test

		public void getDuplicateRecordsTestCaseWithOutDuplilcate() {

			List<CustomerStatment> inputList = Arrays.asList(

					new CustomerStatment(12345, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),

					new CustomerStatment(12344, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			List<CustomerStatment> duplicateRecords = readRecordsAndValidation.findDuplicateRecords(inputList);

			assertEquals(0, duplicateRecords.size());



		}



		/**

		 * Positivie case checking - Not failure records

		 */

		@Test

		public void getEndBalanceErrorRecordsTestCaseWithWrongValue() {

			List<CustomerStatment> inputList = Arrays.asList(

					new CustomerStatment(172833, "NL69ABNA0433647324", 32.76, 49.03, "Tickets for Willem Theuß", 81.79),

					new CustomerStatment(172833, "NL69ABNA0433647324", 32.76, 49.03, "Tickets for Willem Theuß", 81.79));

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			List<CustomerStatment> endBalanceErrorRecords = readRecordsAndValidation.findEndBalanceErrorRecords(inputList);

			assertEquals(0, endBalanceErrorRecords.size());



		}



		/**

		* Negative case checking - Failure records

		 */

		@Test

		public void getEndBalanceErrorRecordsTestCaseWithCorrectValue() {

			List<CustomerStatment> inputList = Arrays.asList(

					new CustomerStatment(172833, "NL69ABNA0433647324", 32.76, 49.03, "Tickets for Willem Theuß", 82.79),

					new CustomerStatment(172833, "NL69ABNA0433647324", 32.76, 49.03, "Tickets for Willem Theuß", 83.79));

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			List<CustomerStatment> endBalanceErrorRecords = readRecordsAndValidation.findEndBalanceErrorRecords(inputList);

			assertEquals(2, endBalanceErrorRecords.size());

		}



		/**

		* Poistive case checking

		 */

		@Test
		public void readRecordsFromCSVTestCase() {

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			File inputFile = new File("records.csv");

			try {

				int totalLineInInputCSV = RecordsCount.getRecordsCount(inputFile);

				List<CustomerStatment> extractedRecords = readRecordsAndValidation.readRecordsFromCSV(inputFile);

				assertEquals(totalLineInInputCSV-1, extractedRecords.size());

			} catch (IOException e) {

				Assert.fail("File processing error!!" + e.getMessage());

				e.printStackTrace();

			} catch (Exception e) {

				Assert.fail(e.getMessage());

				e.printStackTrace();

			}

		}



		/**

		* Positive case checking
		 */

		@Test
		public void readRecordsFromXMLTestCase() {

			ReadRecordsAndValidation readRecordsAndValidation = new ReadRecordsAndValidation();

			File inputFile = new File("records.xml");

			try {

				int totalLineInInputXML = 10; /// let. input XML file has 10 records.

				List<CustomerStatment> extractedRecords = readRecordsAndValidation.readRecordsFromXML(inputFile);

				assertEquals(totalLineInInputXML, extractedRecords.size());

			} catch (Exception e) {

				Assert.fail(e.getMessage());

				e.printStackTrace();

			}

		}
	}
