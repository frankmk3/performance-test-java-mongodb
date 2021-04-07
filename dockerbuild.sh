#!/usr/bin/env bash

#generate backend jar
sh gradlew build

#copy jar file
cp build/libs/performance-test-java-mongodb.jar ./dockercompose/performance-test-java-mongodb/app.jar

#start the containers
docker-compose up -d