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
* https://dzone.com/articles/spring-boot-rest-api-projects-with-code-examples
* https://medium.com/@salisuwy/building-a-spring-boot-rest-api-a-php-developers-view-part-i-6add2e794646
* http://www.springboottutorial.com/spring-boot-rest-api-projects-with-code-examples