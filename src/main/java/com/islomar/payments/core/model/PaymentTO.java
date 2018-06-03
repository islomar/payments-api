package com.islomar.payments.core.model;

import java.util.Map;

public class PaymentTO {
    private final String type;
    private final String organisationId;
    private final Map<String, Object> attributes;

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
