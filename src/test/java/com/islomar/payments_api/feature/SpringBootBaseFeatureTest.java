package com.islomar.payments_api.feature;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringBootBaseFeatureTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootBaseFeatureTest.class);

    private final String SERVER_URL = "http://localhost";
    private final String HELLO_WORLD_ENDPOINT = "/hello-world";
    private final String ROOT_ENDPOINT = "/";
    private TestRestTemplate restTemplate;

    @LocalServerPort
    protected int port;

    public SpringBootBaseFeatureTest() {
        restTemplate = new TestRestTemplate();
    }

    public ResponseEntity<String> helloWorld() {
        return restTemplate.getForEntity(SERVER_URL + ":" + port + HELLO_WORLD_ENDPOINT, String.class);
    }
}