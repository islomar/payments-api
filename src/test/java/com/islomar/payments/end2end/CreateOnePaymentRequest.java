package com.islomar.payments.end2end;

import java.util.Map;

public class CreateOnePaymentRequest {
    private final String type;
    private final String organisationId;
    private final Map<String, Object> attributes;

    public CreateOnePaymentRequest(String type, String organisationId, Map<String, Object> attributes) {
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
