package com.islomar.payments.web.response;

import java.net.URI;
import java.util.Map;

//TODO Look for an alternative for this class, since its only use is the tests
public class DeleteOnePaymentResponse implements PaymentResponse {

    public DeleteOnePaymentResponse() {
    }

    @Override
    public Object getData() {
        return null;
    }

    @Override
    public Map<String, URI> getLinks() {
        return null;
    }

    @Override
    public void addLink(String linkName, URI linkUri) {
        //Useless methods, only necessary because of the interface
    }
}
