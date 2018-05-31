package com.islomar.payments_api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @RequestMapping("/hello-world")
    public String helloWorld() {
        return "Hello world";
    }
}
