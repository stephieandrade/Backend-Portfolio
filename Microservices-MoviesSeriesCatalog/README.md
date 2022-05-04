
Version in [spanish](#microservicios-espaniol) or [english](#microservice-english)
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

<h2 id="microservice-english">
Microservicios
</h2>
