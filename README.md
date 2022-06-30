# Challenge

## :computer: How to execute
Using command line :
Note: You have to be in the project folder where we have the pom.xml 
1. Run mvn install to build the project
2. Run java -jar target/payment-processor 0.0.1-SNAPSHOT to compile and start up the project

Using any of the IDEs (Intellij):
1. Locate PaymentProcessorApplication class and click the play button, it will build and compile the project.

## :memo: Notes
1. I used SpringBoot to start up my project
2. I started by configuring kafka consumer and then created a consumer
method to listen to the messages coming from the two topics (Online and Offline).
3. I Implemented the payment processor using the factory design pattern, I also applied one of the OOP principle to have a 
scalable and clean code, where I had an interface with a method save-payment and then created concrete classes that implemented that method 
based on their behaviours because online payment and offline payment doesn't save payment the same way.
4. Then I created a factory class that had a method process payment which returns the parent class for the two concrete class.
5. I created a utility class for payment processor where I had several methods and also the method that consumes the message
from kafka listener method. 
6. I created a repository interface for both account and payment entity which extends JPARepository class and this uses Hibernate ORM which ensures
 storage and retrieval process between the object and the tables in the database.
7. I used object mapper to change the incoming message type to a PaymentDTO object, then I had a mapper method
that mapped the payment dto to the payment entity which will be saved in the database.
8. I also made use of RestTemplate which is a Sping MVC web client tool, used for interacting with external api in this case,
it was used to make a call to the validate payment endpoint and also the error log endpoint to log errors and save it in the database.
9. I created a Dockerfile for the microservice and added the service to the docker-compose.yml file
## :pushpin: Things to improve
1. I would have used mapstruct for my object mapping
2. I would have also used Spring webflux which is uses reactive programming for it's implementation which is suitable for applications that are event driven, and this handles event loop well
