package com.islomar.payments.web.response;

import java.net.URI;
import java.util.Map;

public interface PaymentResponse {

    Object getData();
    Map<String, URI> getLinks();
    void addLink(String linkName, URI linkUri);
}
