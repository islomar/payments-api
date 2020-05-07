[![Build Status](https://travis-ci.org/islomar/payments-api.svg)](https://travis-ci.org/islomar/payments-api)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.islomar.payments%3Apayments-api&metric=alert_status)](https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api)
[![Coverage Status](https://coveralls.io/repos/islomar/payments-api/badge.svg?branch=master&service=github)](https://coveralls.io/github/islomar/payments-api?branch=master)
<img src="http://validator.swagger.io/validator?url=https://raw.githubusercontent.com/islomar/payments-api/master/openapi/payments-api.yaml">

# Payments REST API
This repo is integrated with TravisCI, with a CD pipeline which executes the isolated and end to end tests, deploying in the end to Heroku and publishing several metrics to SonarCloud and Coveralls:

* TravisCI: https://travis-ci.org/islomar/payments-api  
* Heroku: https://payments-api-islomar.herokuapp.com
* SonarCloud: https://sonarcloud.io/dashboard/index/com.islomar.payments:payments-api
* Coveralls: https://coveralls.io/github/islomar/payments-api  (the coverage is only shown for the isolated tests)

## Features description
This is RESTful Payments API, where it is possible to:
* Fetch	a payment resource.	
* Create, update and delete	a payment resource
* List a collection of payment resources
* Persist resource state (currently only in memory)	

More info about closed and open issues: https://github.com/islomar/payments-api/projects/1


## Prerequisites tu run anything locally
* You need **Java 8** and **Maven >= 3.5** installed.


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
* **Sociable tests**: run `mvn clean test -PintegrationTests` (domain boundary tests)
* **End to end tests** run`mvn clean test -Pend2end` (real Spring Boot tests)
* **All tests**: run `mvn clean test -PallTests` (all the above :-) )

### Manually test
* You can import a Postman collection from here: https://www.getpostman.com/collections/d6792fd6384da2a3ed15

### Mutation testing
* Mutation testing is a good way to check how good your tests are.
* Run `mvn clean test -DwithHistory org.pitest:pitest-maven:mutationCoverage` (example for isolated tests)
* You can see locally the HTML reports created under /target/pit-reports/<timestamp>


## Production environment
* You can access the app here: https://payments-api-islomar.herokuapp.com/
* E.g. https://payments-api-islomar.herokuapp.com/v1/payments


## REST API documentation
* Design diagrams created with Draw.io: https://drive.google.com/drive/folders/1LR-dpCOiYyQpro2T_5weGMEZNjJ9N0Ee?usp=sharing
* https://documenter.getpostman.com/view/2082328/RWEauh9x#4a1f4612-7298-2a7c-b0a2-f5d57b92398c
* Next: use Swagger or something similar.


## Logging, Alerts and Monitoring
* **Papertrail** addon of Heroku to read, search and monitor logs (private unfortunately).
    * Currently, an alert is configured to send to islomar@gmail.com an email in case an ERROR happens.
* **Postman monitor** (private): https://is.gd/2L0TMw
    * Not reliable, the requests used should be changed

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


## Maven checks
* Find available plugin updates: `mvn versions:display-plugin-updates`
* Find available dependency updates: `mvn versions:display-dependency-updates`
* Generate several reports under target/site running `mvn site`
    * It generates pitest reports (mutation testing)
    * Identify dependencies with known vulnerabilities

## New things tried:
* Cucumber (from scratch)
* Papertrail logging and basic alerts
* Micrometer monitoring
* Lombok
* ModelMapper
* draw.io