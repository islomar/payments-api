package com.islomar.payments.rest_api.response;

import java.net.URL;
import java.util.Map;

public interface PaymentResponse {

    Object getData();
    Map<String, URL> getLinks();
}
