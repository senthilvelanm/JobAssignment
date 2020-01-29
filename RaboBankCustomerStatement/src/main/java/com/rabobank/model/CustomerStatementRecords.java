package com.rabobank.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component

@XmlRootElement(name = "records") 

public class CustomerStatementRecords {

		private List<CustomerStatment> customerStatment;
		
		public CustomerStatementRecords() {
			
		}

		public CustomerStatementRecords(List<CustomerStatment> customerStatment) {

			super();

			this.customerStatment = customerStatment;

		}


		

		public List<CustomerStatment> getRecord() {

			return customerStatment;

		}


		@XmlElement(name="record")
		public void setRecord(List<CustomerStatment> customerStatment) {

			this.customerStatment = customerStatment;

		}

	}


