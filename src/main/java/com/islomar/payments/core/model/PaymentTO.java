package com.islomar.payments.core.model;

import java.util.Map;

public class PaymentTO {
    private String type;
    private String organisationId;
    private Map<String, Object> attributes;

    public PaymentTO(){}

    public PaymentTO(String type, String organisationId, Map<String, Object> attributes) {
        this.type = type;
        this.organisationId = organisationId;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
