#### ✏ Autora/Author: [Ayelén Andrade](https://github.com/stephieandrade)

## Tech Stack
<div>
  <img src="https://github.com/get-icon/geticon/raw/master/icons/java.svg" alt="Java" width="21px" height="21px"> JAVA
  <img src="https://github.com/get-icon/geticon/raw/master/icons/spring.svg" alt="Spring" width="21px" height="21px"> SPRING
  <img src="https://github.com/get-icon/geticon/raw/master/icons/docker-icon.svg" alt="docker" width="21px" height="21px"> DOCKER  
</div>

## Description

The project was built using Java 11, Spring framework and microservice architecture 
pattern. The java application works as an Adapter, and is capable of receiving
documents in two different formats, established as the examples given in the task. One
of the document is a JSON file, and the other is a CSV file.
The software application is able to convert each of the documents’ format to FHIR
validated JSON format.

## FHIR format

The chosen FHIR format is a Diagnostic Report, as it includes most of the attributes of the original files: patient ID, date issued, test type
(specimen) and final diagnosis. Differential diagnosis was not included, as those reports
should be addressed in independent reports.

## Validation

An Instance Validator was used to validate the JSON converted files. It was implemented
through FHIR maven dependencies. Instance Validator is validation of the raw or
parsed resource against the official FHIR validation rules

## Service API

API methods and descriptions are detailed in this table:

| Method      | Endpoint           | Description  |
|---|---|---|
| POST      | /reports/converter/json |Recieves a JSON file sent in the body and returns a response with FHIR JSON format of the file.  |
| POST | /reports/converter/csv      |Recieves a CSV file sent in the body and returns a response with FHIR JSON format of the file.|

Documentation of API requests and examples is available in: [POSTMAN url](https://documenter.getpostman.com/view/20744743/Uz5AreLx).

## Deployment

A postman collection was also added to the project to test the APIs with the files. To
execute it, Postman must be installed. Please note that CSV file must be
uploaded manually for the API to be tested.
Deployment
For deployment, all source code will be compiled and packaged as a jar. These jar files
will later be used to create the docker image for each service. Docker needs to be
installed for deployment (Docker Download, n.d.).
For the execution of each application, it will be deployed using a Dockerfile such as:

``` 
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=ResearcherData-0.0.1-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
``` 

The project can be deployed with docker compose using the next command in the root
directory (“../Researcher/”):

