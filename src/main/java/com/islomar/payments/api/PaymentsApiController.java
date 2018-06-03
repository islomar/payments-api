package com.islomar.payments.api;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class PaymentsApiController {

    @RequestMapping("/")
    public String index() {
        return "The server is up and running!";
    }

    @RequestMapping(value = "/v1/payments", method = RequestMethod.GET)
    @ResponseBody
    public FetchAllPaymentsResponse fetchAllPayments(HttpServletRequest request) throws MalformedURLException {
        FetchAllPaymentsResponse fetchAllPaymentsResponse = new FetchAllPaymentsResponse(Collections.emptyList());
        fetchAllPaymentsResponse.addLink("self", currentUrl(request));
        return fetchAllPaymentsResponse;
    }

    private URL currentUrl(HttpServletRequest request) throws MalformedURLException {
        return new URL(request.getRequestURL().toString());
    }

    @ExceptionHandler({Exception.class})
    void handleInternalServerError(HttpServletResponse response) throws IOException {
        response.sendError(INTERNAL_SERVER_ERROR.value());
    }
}
