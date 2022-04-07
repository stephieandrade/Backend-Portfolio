## Name

Shockbyte Interview Scenario

## Description

Creation of a REST API to handle node statistics. This includes data of System uptime, total RAM (integer: bytes), Allocated RAM, Total Disk and Allocated Disk.

For API documentation enter:
http://localhost:8085/swagger-ui.html

## Technologies used

- Java Spring Boot
- Docker
- MySQL

Firstly, I created the model for the table of nodes, and I also built a DTO to transfer the data through the API. On the service, I created the different methods that create, delete, find, update and list the nodes. I used two task schedulers to inject data into the database, and used the findAll() method to only return the nodes that were registered every hour for 24 hours. This way, only recent data is returned by the method.

In the controller process the incoming REST API requests are processed and return response Entities for each method.

Also, some Bad Request Exceptions where added, for example if a node wasn't found in the findById(id) method, or if a node couldn't be deleted.

Finally, a docker image of the mysql database and one of the spring boot application were created, and a docker-compose.yml file was used to manage both image containers.

## Installation

Download the code in this repository. With command line interface, go to the main project folder destination (called '.../Proyecto') and execute docker compose:

```
docker-compose up
```

The app should start after a few seconds.

Use the next line to access mysql database:

```
mysql -u root -p root
```
