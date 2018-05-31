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
    * From a web browser, access http://localhost:9000/hello-world, you should see a "Hello world" message
    * From a terminal, run `curl localhost:9000/hello-world`


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
* **Papertrail** addon of Heroku to monitor logs.


## Basic Monitoring Services
* Based in Micrometer (already included in Spring Boot).
* Some examples in local environment:
    * Health:   http://localhost:9001/monitor/health
    * Info:     http://localhost:9001/monitor/info
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
* Micrometer monitoring

## Pending to take a look
* https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-monitoring.html
* 12factor

## Next steps
* Authentication and authorization
* Secure monitoring endpoints: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints-security
* HTTPS
* Serious logging and monitoring: e.g. ELK or Sentry or Prometheus+Grafana, etc.
* Serious production environment: e.g. DigitalOcean, AWS, Google Cloud, etc.