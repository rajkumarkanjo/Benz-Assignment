# Benz-Assignment

Project Objective : Application is to create a Microservices backend to securely store data in a file format and allow the user to read and update when required. 
                    The backend should support storing the data in both CSV and XML file format.
                    
Technology Stack : java ,Spring-Boot, Rest API, Microservices, build tool - Gradle.


Request Format : 
                   {
    "name": "John Raj",
    "dob": "12-05-2009",
    "salary": "85000",
    "age": "30"
}

High Level Design :  There are 3 Microservices :-
                        1 . Eureka-Server
		                    2 . Consumer-service
		                    3 . Producer-service
		 
		 
	 1 . Eureka-Server : This is Standalone service to register the microservices. In this project we will register consumer and producer service.
	 
	 2 . Consumer-service : This service is to take request from the user and does basic validation and calls the producer-service.

   3 . Consumer-service : This service will receive the request from consumer-service and based on the fileType(CSV,XML) it will process.
	 
	 
	 All these Microservices are cummunicating using declarative web service Feign-client.
   

 
API Design : 

               a.	Store – to save new data 
			   
				       	    url: http://consumer-service/api/v1/save
				      	    Method: POST , application/json 
					          Header: key - fileType , value - CSV/XML
			    
               b.	Update – to update existing data
			   
					          url: http://consumer-service/api/v1/update
					          Method: PUT , application/json 
					          Header: key - fileType , value - CSV/XML
					          Note:for update operation, 'name' and 'dob' is the mandatory attributes.
				 
               c.	Read – to read existing data from the files
			   
                     url: http://consumer-service/api/v1/read/{name}
				             Method: GET , application/json 
                     Note : for read operation , name mandatory attributes which will be passed in the uri as path variable.

