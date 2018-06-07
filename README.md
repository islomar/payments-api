[![Build Status](https://travis-ci.org/islomar/payments-api.svg)](https://travis-ci.org/islomar/payments-api)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.islomar.payments%3Apayments-api&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api)
[![Coverage Status](https://coveralls.io/repos/islomar/payments-api/badge.svg?branch=master&service=github)](https://coveralls.io/github/islomar/payments-api?branch=master)


# Payments REST API
This repo is integrated with TravisCI, with a CD pipeline which executes the isolated and end to end tests, deploying in the end to Heroku and publishing several metrics to SonarCloud and Coveralls:

* TravisCI: https://travis-ci.org/islomar/payments-api  
* Heroku: https://payments-api-islomar.herokuapp.com
* SonarCloud: https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api
* Coveralls: https://coveralls.io/github/islomar/payments-api


## Prerequisites tu run anything locally
* You need **Java 8** and **Maven 3.x** installed.


## How to run the REST API server locally
1. In order to start it up, you have two options:
    * `mvn spring-boot:run`, or
    * `mvn clean package && java -jar target/payments-api-1.0.0.jar`
2. To check that it is up and running, you have two options:
    * From a web browser, access http://localhost:9000
    * From a terminal, run `curl localhost:9000`
    * In either case, you should see a "The server is up and running!" message


## Testing
You can see the resulting tests executed in TravisCI: https://travis-ci.org/islomar/payments-api

### How to run the automated tests locally
* **Isolated tests**: run `mvn clean test`
* **End to end tests** run`mvn clean test -Pend2end`

### Postman testing
The API is "documented" 

### Manually test
* You can import a Postman collection from here: https://www.getpostman.com/collections/d6792fd6384da2a3ed15

### Mutation testing
* Mutation testing is a good way to check how good your tests are.
* Run `mvn clean test && mvn -DwithHistory org.pitest:pitest-maven:mutationCoverage` (you need to run the tests first)
* You can see locally the HTML reports created under /target/pit-reports/<timestamp>


## Production environment
* You can access the app here: https://payments-api-islomar.herokuapp.com/
* E.g. https://payments-api-islomar.herokuapp.com/v1/payments


## REST API documentation
* https://documenter.getpostman.com/view/2082328/RWEauh9x#4a1f4612-7298-2a7c-b0a2-f5d57b92398c
* Next: use Swagger or something similar.


## Logging, Alerts and Monitoring
* **Papertrail** addon of Heroku to read, search and monitor logs (private unfortunately).
    * Currently, an alert is configured to send to islomar@gmail.com an email in case an ERROR happens.
* **Postman monitor** (private): https://is.gd/2L0TMw
    * I would get an email if the server were down

### Basic Monitoring Services
* Based in Micrometer (already included in Spring Boot).
* Some examples in local environment:
    * Health:   http://localhost:9000/monitor/health
    * Info:     http://localhost:9000/monitor/info
* Some examples in Production:
    * All the published endpoints: http://payments-api-islomar.herokuapp.com/monitor
    * Health:   http://payments-api-islomar.herokuapp.com/monitor/health
    * Info:     http://payments-api-islomar.herokuapp.com/monitor/info
* More info about the endpoints:
    * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints-exposing-endpoints


## New things tried:
* Cucumber
* Papertrail logging and basic alerts
* Micrometer monitoring
* Lombok
* ModelMapper


## Next steps
See the open issues at https://github.com/islomar/payments-api/projects/1


## References used

### Spring Boot
* https://dzone.com/articles/spring-boot-rest-api-projects-with-code-examples
* https://medium.com/@salisuwy/building-a-spring-boot-rest-api-a-php-developers-view-part-i-6add2e794646
* http://www.springboottutorial.com/spring-boot-rest-api-projects-with-code-examples
* https://spring.io/guides/gs/actuator-service/
* Publish info with actuator: http://www.baeldung.com/spring-boot-info-actuator-custom
* Unit test for Spring MVC Controller: 
    * https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#spring-mvc-test-server
    * https://docs.spring.io/spring-security/site/docs/current/reference/html/test-mockmvc.html
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