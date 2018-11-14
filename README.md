A brokerless Spring Cloud Stream binder that can be used for inter process communication.

## Usage

Add this jar to the classpath of a Spring Cloud Stream application and set "brokerless" binder type in your binder configuration. 

Example config: 

    spring.cloud.stream.binders.myBinderName.type=brokerless

## Brokerless Binder Overview

This is very simple broker implementation that can be used without a broker hence the name 'brokerless'. 
This binder is intended to be used on development environment where you don't want to use additional brokers like RabbitMQ, Kafka.

## Example flow

Lets assume that you have configured a couple of stream listeners aka handlers named `myFirstHandler` and `mySecondHandler` that can handle/listen on `myEventName`
       
        spring.cloud.stream.bindings.myFirstHandler.group=com.example.MyEventListener
        spring.cloud.stream.bindings.myFirstHandler.binder=myBinderName
        spring.cloud.stream.bindings.myFirstHandler.destination=myEventName
        
        spring.cloud.stream.bindings.mySecondHandler.group=com.example.MyEventListener
        spring.cloud.stream.bindings.mySecondHandler.binder=myBinderName
        spring.cloud.stream.bindings.mySecondHandler.destination=myEventName


This binder will map the two handlers `myFirstHandler` and `mySecondHandler` to `myEventName` and
once `myEventName` is published, both `myFirstHandler` and `mySecondHandler` will be invoked with the message provided in the `myEventName`

## How to get this project in your build?

##### 1. Add the JitPack repository to your build file

  	<repositories>
  		<repository>
  		    <id>jitpack.io</id>
  		    <url>https://jitpack.io</url>
  		</repository>
  	</repositories>
    	
##### Step 2. Add the dependency

  	<dependency>
  	    <groupId>com.github.Seavus</groupId>
  	    <artifactId>spring-cloud-stream-binder-brokerless</artifactId>
  	    <version>1.0</version>
  	</dependency>


    

