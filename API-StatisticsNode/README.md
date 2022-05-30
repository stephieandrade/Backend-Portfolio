#### ✏ Autora/Author: [Ayelén Andrade](https://github.com/stephieandrade)

## Tech Stack
<div>
  <img src="https://github.com/get-icon/geticon/raw/master/icons/java.svg" alt="Java" width="21px" height="21px"> JAVA
  <img src="https://github.com/get-icon/geticon/raw/master/icons/spring.svg" alt="Spring" width="21px" height="21px"> SPRING
  <img src="https://github.com/get-icon/geticon/blob/master/icons/mysql.svg" width="24px" height="24px" alt="MYSQL"> MYSQL   
  <img src="https://github.com/get-icon/geticon/raw/master/icons/docker-icon.svg" alt="docker" width="21px" height="21px"> DOCKER  
</div>

## Description

Creation of a REST API to handle node statistics. This includes data of System uptime, total RAM (integer: bytes), Allocated RAM, Total Disk and Allocated Disk.

## Technologies used

- Java Spring Boot
- Docker
- MySQL

## Node Microservice

Firstly, I created the model for the table of nodes, and I also built a DTO to transfer the data through the API. On the service, I created the different methods that create, delete, find, update and list the nodes. I used two task schedulers to inject data into the database, and used the findAll() method to only return the nodes that were registered every hour for 24 hours. This way, only recent data is returned by the method.

Method | Endpoint | Description
---|---|---
GET | /node/ |Lists all nodes.
GET | /node/{ID} |Gets a node according to the ID.
POST | /node/ |Creates a new node.
UPDATE | /node/{ID} |Updates a node according to the ID.
DELETE | /node/{ID} |Deletes a node according to the ID.

## Deployment

Finally, a docker image of the mysql database and one of the spring boot application were created, and a docker-compose.yml file was used to manage both image containers.

## Installation

Download the code in this repository. With command line interface, go to the main project folder destination (called '.../Proyecto') and execute docker compose:

```
docker-compose up -d
```

The app should start after a few seconds.

## Documentation 

[Link to API documentation in Postman](https://documenter.getpostman.com/view/20744743/UyxbqV8m)
