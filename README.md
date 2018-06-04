[![Build Status](https://travis-ci.org/islomar/payments-api.svg)](https://travis-ci.org/islomar/payments-api)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.islomar.payments%3Apayments-api&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api)

# Payments REST API

## Prerequisites tu run anything locally
* You need **Java 8** and **Maven** installed.


## How to run the API locally
1. In order to start it up, you have two options:
    * `mvn spring-boot:run`, or
    * `mvn clean package && java -jar target/payments-api-1.0.0.jar`
2. To check that it is up and running, you have two options:
    * From a web browser, access http://localhost:9000
    * From a terminal, run `curl localhost:9000`
    * In either case, you should see a "The server is up and running!" message


## How to run the tests locally
Run `mvn clean test`


## Production environment
You can access the app here: https://payments-api-islomar.herokuapp.com/


## Continuous Deployment
The main frameworks, technologies and platforms used have been:
* **Spring Boot**
* **Heroku**: 
    * The GitHub repository is connected to Heroku, as well to TravisCI (for CI).
    * Each time a push is done to the GitHub repository, it builds the application and executes all the tests (at TravisCI). If the tests execution is successful, then the deployment to Heroku is done... et voilà!!! :-)


## Logging
* **Papertrail** addon of Heroku to read, search and monitor logs.
* Currently, an alert is configured to send to islomar@gmail.com an email in case an ERROR happens.


## Basic Monitoring Services
* Based in Micrometer (already included in Spring Boot).
* Some examples in local environment:
    * Health:   http://localhost:9000/monitor/health
    * Info:     http://localhost:9000/monitor/info
* Some examples in Production:
    * Health:   http://payments-api-islomar.herokuapp.com/monitor/health
    * Info:     http://payments-api-islomar.herokuapp.com/monitor/info
* More info about the endpoints:
    * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints-exposing-endpoints

## Integrations
* Heroku: https://payments-api-islomar.herokuapp.com
* TravisCI: https://travis-ci.org/islomar/payments-api
* SonarCloud: https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api


## New things tried:
* Cucumber
* Papertrail logging and basic alerts
* Micrometer monitoring


## Pending to take a look
* Return Location when creating a resource: https://spring.io/guides/tutorials/bookmarks/
* https://crunchify.com/hashmap-vs-concurrenthashmap-vs-synchronizedmap-how-a-hashmap-can-be-synchronized-in-java/
* Validations
* Swagger
* https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-monitoring.html
* 12factor


## Principles
* Decouple the domain from the frameworks. Use of DDD and hexagonal architecture. 


## Next steps
* Authentication and authorization: https://github.com/islomar/payments-api/issues/14
* Publish monitoring endpoints on a different port
* Secure monitoring endpoints: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints-security
* HTTPS
* Serious logging and monitoring: e.g. ELK or Sentry or Prometheus+Grafana, etc.
* Serious production environment: e.g. DigitalOcean, AWS, Google Cloud, etc.

## Comments
* The response example given is neither HAL nor HATEOAS, but something ad-hoc.

## References

### Spring Boot
* https://dzone.com/articles/spring-boot-rest-api-projects-with-code-examples
* https://medium.com/@salisuwy/building-a-spring-boot-rest-api-a-php-developers-view-part-i-6add2e794646
* http://www.springboottutorial.com/spring-boot-rest-api-projects-with-code-examples
* https://spring.io/guides/gs/actuator-service/
* Publish info with actuator: http://www.baeldung.com/spring-boot-info-actuator-custom
* Unit test for Spring MVC Controller: https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-server
* Testing and use of MockMvc: 
    * https://spring.io/guides/gs/testing-web/
    * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-mvc-tests

### Cucumber
* https://docs.cucumber.io/guides/anti-patterns/


### Cucumber + Spring
* http://www.baeldung.com/cucumber-spring-integration
* REST API + Cucumber: https://www.blazemeter.com/blog/api-testing-with-cucumber-bdd-configuration-tips
* Spring Boot + Cucumber: 
    * https://dzone.com/articles/spring-boot-integration-test-with-cucumber-and-jen
    * https://thepracticaldeveloper.com/2018/03/31/cucumber-tests-spring-boot-dependency-injection/
* Code examples:
    * https://github.com/Romeh/spring-boot-sample-app
    * https://github.com/microservices-practical/microservices-v9
    * https://github.com/spring-projects/spring-framework/tree/master/spring-test/src/test/java/org/springframework/test/web/servlet/samples
    
    
### REST API
* https://hackernoon.com/restful-api-designing-guidelines-the-best-practices-60e1d954e7c9

### Mapper
* https://medium.com/@auth0/automatically-mapping-dto-to-entity-on-spring-boot-apis-c2aa8f67ec91
* http://joshlynn.net/2016/02/28/building-a-restful-spring-boot-application-with-data-jpa-part-2/
* https://auth0.com/blog/automatically-mapping-dto-to-entity-on-spring-boot-apis/
* http://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application

### JSON
* http://www.makeinjava.com/convert-list-objects-tofrom-json-java-jackson-objectmapper-example/
* https://www.mkyong.com/java/jackson-tree-model-example/
* https://www.mkyong.com/java/how-to-convert-java-object-to-from-json-jackson/
* http://www.baeldung.com/jackson-object-mapper-tutorial
* https://www.javatips.net/api/com.fasterxml.jackson.databind.jsonnode

### Hypermedia
* https://spring.io/guides/gs/rest-hateoas/

### Exception handling
* http://www.springboottutorial.com/spring-boot-exception-handling-for-rest-services
* https://www.toptal.com/java/spring-boot-rest-api-error-handling


### Persistence
* https://github.com/codurance/light-access