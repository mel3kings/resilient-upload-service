  Resilient Service

 # Architectual Notes
 ![alt text](https://github.com/mel3kings/resilient-upload-service/blob/master/resilient-service/architectures.png)
  
 ## Technology Stack
* Apache Kafka - Messaging Queue for queueing all incoming request, this enables us to handle large amount of request
* Grails - also known as Groovy on Grails, language focused more on the logic, rather than plumbing
* Docker - for creating images of the application, and run containers based on said images
* Gradle - Dependency Management
 
 ### Implementations Notes
 Concepts introduced to make sure our application is resilient:
 * Throttling - we introduce the concept of throttling here, with the Atomic Library, we are able to check how many request
are coming at given set of time. We do not necessarily accept all the request at the same time. If the number of request exceeds
ideal scenario, we put the request on hold for a number of time before actually submitting to the service
 * Messaging Queue - by utilizing Apache Kafka, a Queueing mechanism is introduced, after throttling the request, the service
 then immediately responds that the request has been accepted. At this point we have only saved the request in the queue
 * Cron Job - After we have responded accepted, a cron job is run at the background consuming from the queue one by one 
 and the actual service performs what it actually needs to be done
 * Asynchronous Processing - In order for the the user to know the actual status of his request he has to perform a subsequent 
 call with a corresponding  id to the service again to actually see the status. 
 
### Known Issues
 * With the throttling enabled, some request might not receive an immediate response, this is the original design to hold the request
 * This a bare-bone application, nothing is actually done by the service after it has received the request. Perhaps it can be part 2
 * Folder structure of project is not ideal

 ### Room For improvement
 * You can change the amount of brokers in Kafka to handle more Request
 * You can tweak the throttling for more optimization, rather than waiting we can have them retry again later
 
 ### Running 
 Running Grails app (under resilient-service folder)
 ```
grails run-app 
 ```
Running A Basic Kafka  
```
docker run --rm -it -p 2181:2181 -p 3030:3030 -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 9092:9092 -e ADV_HOST=192.168.99.100 --name kafka-dev landoop/fast-data-dev
```
Running commands to your Kafka from Docker
```
docker exec -it kafka-dev bash 
```
Creating a Kafka Topic
```
kafka-topics --create --topic first-topic --zookeeper 192.168.99.100:2181 --replication-factor 1 --partitions 3
```
### Request Response
POST METHOD
```
http://localhost:8080/upload/send
```
Request Body
```
{
  "location":"desktop",
  "type":"sample",
  "description":"Sample NEW"
}
```

GET Method Sample Request
```
http://localhost:8080/upload/sample
```
GET Health Check
```
http://localhost:8080/upload
```
 ## Authors
 * **Melchor Tatlonghari** - *Initial work* - [mel3kings](https://github.com/mel3kings)
 
 
 
 
 
