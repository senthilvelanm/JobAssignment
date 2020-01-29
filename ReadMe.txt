Rabobank Customer Statement Processor
-------------------------------------

Technologies 		 			: Java 1.8, Spring, Spring boot and Restful services
Build 							: Maven
Endpoint checking/testing tool	: Postman
Development IDE 				: Eclipse IDE for Enterprise Java Developer with STS
Server							: Tomcat
Browser							: Internet explorer

Concept implemented in Application: 

Design flow/Business/packages  : Controller, Service, model , util , exception handling and Test

Features used : Common exception handling, slfj4- logging , Junit, actuator, file upload and read/process data from csv/JAXB supported files.


---------------------
Requirement/output:
--------------------

There are two validations:

•all transaction references should be unique

•the end balance needs to be validated

At the end of the processing, a report needs to be created which will display both the transaction reference and description of each of the failed records

Logical applied:

Get data/file from front-end and processing/validating the business logic in controller/services layer based on above output requirements.
Finally output will pass to fron-end which one duplicated records and end balance value based on validations. At the same time we are handling common exception and throw the exception to front-end if any errors/exception in application. For debuging purpose, hanlding logging the information in all the main flows/methods.

Output/Test results attached : Application_TestResults.docx

Standalone Application Installation :
---------------------------------------

1. Copy the RaboBankCustomerStatement-0.0.1-SNAPSHOT.jar from \target\git folder to your local folder 
2. For testing, copy records.xml,records.csv files to same location if you want test
3. java -jar RaboBankCustomerStatement-0.0.1-SNAPSHOT.jar from command line of your folder location.


Maven Application Installation :
---------------------------------

1. Download the RaboBankCustomerStatement folder/project and extracted into your local folder
2. import RaboBankCustomerStatement as maven project in IDE
3. Do maven update project and maven build( clean install or clean package )
4. start the spring boot application
5. Test the below url in postman tool for the application status/functionality check.

http://localhost:8081/rabobank
http://localhost:8081/rabobank/customerStatment


Improvement suggestion on this application:
1. File storage into drive/DB and will process from drive/DB based on requirement
2. Logging may be better to use and store into file system for tracking/reference.
3. Common exception pass to UI for handling perfectly.

