
#### Version in [SPANISH](#microservicios-espaniol) or [ENGLISH](#microservice-english)
#### ✏ Autora/Author: [Ayelén Andrade](https://github.com/stephieandrade)

## Tech Stack
<div>
  <img src="https://github.com/get-icon/geticon/raw/master/icons/java.svg" alt="Java" width="21px" height="21px"> JAVA
  <img src="https://github.com/get-icon/geticon/raw/master/icons/spring.svg" alt="Spring" width="21px" height="21px"> SPRING
  <img src="https://github.com/get-icon/geticon/blob/master/icons/mysql.svg" width="24px" height="24px" alt="MYSQL"> MYSQL   
  <img src="https://github.com/get-icon/geticon/raw/master/icons/mongodb-icon.svg" alt="MongoDB" width="21px" height="21px"> MONGODB
  <img src="https://github.com/get-icon/geticon/raw/master/icons/docker-icon.svg" alt="docker" width="21px" height="21px"> DOCKER  
</div>

<h2 id="microservicios-espaniol">
Microservicios
</h2>
Proyecto realizado usando el patrón de arquitectura de microservicios con Spring Cloud, Spring Boot, Rabbit, Zipkin y Docker.

## Servicios

Los servicios funcionales siguen estando descompuestos en tres servicios básicos.

<ul>
  <li>Movie</li>
  <li>Serie</li>
  <li>Catalog</li>
</ul>
Los servicios Movie y Serie pueden probarse, construirse y desplegarse de forma independiente entre ellos, pero el servicio Catalog depende del funcionamiento de los servicios anteriores.

## Movie Service

Contiene la API relacionada con la creación y visualización de la información de cada película (Movie). En este servicio también usamos **RabbitMQ** como broker de mensajería para el registro de nuevas películas que posteriormente será un evento para Catalog service. Este servicio utiliza como motor de base de datos para su almacenamiento **MySQL**.

| Método       | Endpoint           | Descripción  |
|---|---|---|
| GET      | /movies/genre/{GENRE} |Obtiene un listado de peliculas segun el género |
| GET      | /movies/{ID}     |Obtiene una película segun el id |
| POST | /movies/salvar      |Guarda una película en la base de datos|

## Serie Service

Contiene la API relacionada con la visualización de la información de cada Serie. En este servicio también usamos **RabbitMQ** como broker de mensajería para el registro de nuevas series que posteriormente será un evento para Catalog service. Este servicio utiliza como motor de base de datos para su almacenamiento **MongoDB**.

| Método       | Endpoint           | Descripción  |
|---|---|---|
| GET      | /series/{GENRE} |Obtiene un listado de series segun el género |
| POST | /series/guardar      |Guarda una serie en la base de datos|

## Catalog Service

Contiene la API relacionada con la visualización de la información de cada catalogo (Catalog). En este servicio también usamos **RabbitMQ** como broker de mensajería suscribiéndose a las queues en las que el servicio Movie service y Movie Service se comunican, esto con el fin de actualizar los registros de Catalog en base a los últimos registros de Movie y Serie. Este servicio utiliza como motor de base de datos para su almacenamiento **MongoDB**.

| Método       | Endpoint           | Descripción  |
|---|---|---|
| GET      | /catalog/{GENRE} |Obtiene un catálogo de películas y series segun el género |

## Infraestructura

Para la arquitectura de microservicios se basa en Spring Cloud que proporciona amplias herramientas de soporte como el balanceador de carga, el registro de servicios, la monitorización y la configuración.

![68747470733a2f2f692e6962622e636f2f4832306d7048392f4172717569746563747572612d4d6963726f736572766963696f2e706e67](https://user-images.githubusercontent.com/37404936/166659076-4b60f538-e1f4-4ef0-8e84-f1e606460182.png)

## Config

Spring Cloud Config es un servicio de configuración centralizado y escalable horizontalmente para sistemas distribuidos. Utiliza una capa de repositorio que actualmente admite el almacenamiento local y Git.

[Repositorio](https://github.com/stephieandrade/configuration-files-exam) con archivos de configuración para los microservicios.

## Service Discovery

En este proyecto utilizo Netflix Eureka. Eureka es un buen ejemplo del patrón de descubrimiento del lado del cliente, cuando el cliente es responsable de determinar las ubicaciones de las instancias de servicio disponibles (utilizando el servidor de Registro) y el equilibrio de carga de las solicitudes a través de ellos.

```
spring:
  application:
    name: movie-service

eureka:
  client:
    service-url:
      defaultZone: http://eureka-server:8761/eureka/
```

Ahora, al iniciarse la aplicación, se registrará en el servidor Eureka y proporcionará metadatos, como el host y el puerto, la URL del indicador de salud, la página de inicio, etc. Eureka recibe mensajes de heartbeat de cada instancia perteneciente a un servicio y registrando el servicio dentro del servidor.

## Despliegue

Para el despliegue, todo el código fuente será compilado y empaquetado como un jar. Estos archivos jar se utilizarán posteriormente para crear la imagen de cada servicio.

Para la ejecución de cada aplicación será implementado mediante un archivo Dockerfile como por ejemplo

```
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=catalog-service-1.0.0-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

Para la orquestación y despliegue de todos los servicios mediante los archivos Dockerfile, se utiliza Docker compose y para la ejecución del mismo se utiliza el siguiente comando en la consola

```
docker compose up -d
```

## Documentación en Postman

[Link a documentación](https://documenter.getpostman.com/view/20744743/UyrEiFkg)

<h2 id="microservice-english">
Microservices
</h2>

## Services

Functional services continue to be broken down into three basic services.

<ul>
  <li>Movie</li>
  <li>Serie</li>
  <li>Catalog</li>
</ul>

The Movie and Series services can be tested, built, and deployed independently of each other, but the Catalog service depends on the operation of the above services.

## Movie Service

Contains the API related to the creation and visualization of the information of each movie (Movie). In this service we also use **RabbitMQ** as a messaging broker for the registration of new movies that will later be an event for the Catalog service. This service uses **MySQL** as a database engine for its storage.

| Method      | Endpoint           | Description  |
|---|---|---|
| GET      | /movies/genre/{GENRE} |Gets a list of movies by genre |
| GET      | /movies/{ID}     |Gets a movie by id |
| POST | /movies/salvar      |Posts a movie in the database|

## Serie Service

It contains the API related to the visualization of the information of each Series. In this service we also use **RabbitMQ** as a messaging broker for the registration of new series that will later be an event for the Catalog service. This service uses **MongoDB** as a database engine for its storage.

| Method      | Endpoint           | Description  |
|---|---|---|
| GET      | /series/{GENRE} |Gets a list of series by genre |
| POST | /series/guardar      |Posts a serie in the database|

## Catalog Service

Contains the API related to the visualization of the information of each catalog (Catalog). In this service we also use **RabbitMQ** as a messaging broker subscribing to the queues in which the Movie service and Movie Service communicate, this in order to update the Catalog records based on the latest Movie records and Series. This service uses **MongoDB** as a database engine for its storage.

| Method      | Endpoint           | Description  |
|---|---|---|
| GET      | /catalog/{GENRE} |Gets a catalog of movies and series according to genre |

## Infrastructure

For the microservices architecture it is based on Spring Cloud that provides extensive support tools such as load balancer, service registry, monitoring and configuration.

![68747470733a2f2f692e6962622e636f2f4832306d7048392f4172717569746563747572612d4d6963726f736572766963696f2e706e67](https://user-images.githubusercontent.com/37404936/166659076-4b60f538-e1f4-4ef0-8e84-f1e606460182.png)

## Config

Spring Cloud Config is a centralized, horizontally scalable configuration service for distributed systems. It uses a repository layer that currently supports local storage and Git.

[Repositorio](https://github.com/stephieandrade/configuration-files-exam) with configuration files for the microservices.

## Service Discovery

In this project I use Netflix Eureka. Eureka is a good example of the client-side discovery pattern, where the client is responsible for determining the locations of available service instances (using the Registry server) and load balancing requests across them.

```
spring:
  application:
    name: movie-service

eureka:
  client:
    service-url:
      defaultZone: http://eureka-server:8761/eureka/
```

Now, when the application starts, it will register with the Eureka server and provide metadata such as host and port, health indicator URL, home page, etc. Eureka receives heartbeat messages from each instance belonging to a service and registering the service within the server.

## Deployment

For deployment, all source code will be compiled and packaged as a jar. These jar files will be used later to create the image for each service.

For the execution of each application it will be implemented through a Dockerfile file such as

```
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=catalog-service-1.0.0-SNAPSHOT.jar
COPY target/${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
```

For the orchestration and deployment of all the services through the Dockerfile files, Docker compose is used and for its execution the following command is used in the console

```
docker compose up -d
```

## Documentation in Postman

[Link to se documentation](https://documenter.getpostman.com/view/20744743/UyrEiFkg)



