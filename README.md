![Reactive non-blocking Application with Spring Boot 2 on Spring WebFlux stack, Spring Data Reactive Repositories](logo.png)

# Overview
A Sample application built with Spring Boot 2 on Spring WebFlux stack, integrated with Spring Data Reactive Repositories with MongoDB and Azure CosmosDB to demonstrate an end to end non-blocking application. The codebase consists of two projects a REST API called ReactiveRecommendationService and a Web App which depends on the REST API called ReactiveReadingListWebApp.

Specifically, the following capabilities are demonstrated:
* Spring Boot 2 on WebFlux - Functional Routing, thymeleaf templates, Dependency Injection etc
* Netty HTTP server backend
* Calling REST APIs with WebClient
* Spring Data Reactive Repositories with MongoDB
* Spring Reactor 
* Using Cosmos DB Mongo API from Spring Data Reactive Repositories with MongoDB

# Getting started

## First:
 * Java 8
 * maven
 * Create a Cosmos DB Mongo collection named "ReadingList". use /reader as the "Shard Key"

## Then:
* Update the following properties in application.properties for ReactiveReadingListWebApp 
  - spring.data.mongodb.uri  
* mvn spring-boot:run - from ReactiveRecommendationService the to start the REST API 
* mvn spring-boot:run - from ReactiveReadingListWebApp to start the Web Application
* Access the WebApp at http://localhost:8080/readingList

##### NOTE: You can also run the application against Mongo DB


