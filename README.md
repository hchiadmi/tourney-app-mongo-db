# tourney-app-mongo-db

# Getting Started

## Prerequisites :

Java version 17
Docker image of mongoDb Server or mongoDbServer
Angular for front

## Docker image of mongoDb server :

Write these command lines in a terminal : 

```
docker pull mongo
docker run -d -p 27017:27017 --name test-mongo mongo:latest
```

Now you have a mongoDb Server up and running.

## Start springBoot Application : 

### Via Intellij

Go to package com.betclic.tourney and start main function in file TourneyApplication. It will run on port 8080.

### Via CommandLine

Type in project repository :
```
./gradlew bootRun
```
## Postman testing: 

A postman collection is available for testing. Feel free to use it or use your own

## Front

git repository : https://github.com/hchiadmi/AngularTourneyApp.git

You can launch this angular project for a UI.  
It has access to the springBootApplication and will be launched on port 4200.  
To launch the angular project type in terminal the following command line : 

```
ng serve
```

You can access the UI page in port http://localhost:4200/
