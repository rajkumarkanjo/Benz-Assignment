# Benz-Assignment

Project Objective : Application to create a Microservices backend to securely store data in a file format and allow the user to read and update when required. 
                    The backend should support storing the data in both CSV and XML file format.
                    
Technology Stack : java ,Spring-Boot, Rest API, Microservices, build tool - Gradle.


Request Format : 
                   {
    "name": "Raj Kumar",
    "dob": "12-05-1995",
    "salary": "85000",
    "age": "26"
}

High Level Design :  There are 3 Microservices :-
                        1 . Eureka-Server
		                    2 . Consumer-service
		                    3 . Producer-service
		 
		 
	 1 . Eureka-Server : This is Standalone service to register the microservices. In this project we will register consumer and producer service.
	 
	 2 . Consumer-service : This service is to take request from the user and does basic validation and calls the producer-service.

     3 . Producer-service : This service will receive the request from consumer-service and based on the fileType(CSV,XML) it will process.
	 
	 
 All these Microservices are communicating using declarative web service Feign-client.
   

 
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
					     
					     
How To Run : 

          1 > System Requirements : JDK 1.8+ version installed, IDE (Eclipse,IntelliJ IDEA) etc .
	  
          2> GitHUb Repository : https://github.com/rajkumarkanjo/Benz-Assignment.git
	  
	      3> create a workspace in your system, clone the project from the GitHUb Repository( git clone <repository>)
	  
	      4> Go to File> Open > (select service one by one and import in your IDE)
	  
	  Once all the imports are done : 
	  
	     First Run your Eureka-server Application, http://localhost:8761/ go to this url and check , whether it up or not. once it is up
	     Run your consumer-service and producer-service Application.
	     Once both the Services get started, it will register itself on Eureka Server. you can go and check on the url.
	     
	     ![Eureka-Server](https://user-images.githubusercontent.com/29033171/135751090-cbac90f8-1272-404e-adc4-a0c1dee53fb1.jpg)

	  
	  Once All three 3, Microservice are started, you can form the json request as mentioned on this page and hit the API mentioned in API design.
	  
Happy Coding !	  
	  
	 
	 
	 
	
	 
	 
	 
	 
	 
					     

