package com.islomar.payments.rest_api;

public class CreateOnePaymentResponse {

    private String type;
    private String organisationId;

    public CreateOnePaymentResponse(){}

    public CreateOnePaymentResponse(String type, String organisationId) {
        this.type = type;
        this.organisationId = organisationId;
    }

    public String getType() {
        return type;
    }

    public String getOrganisationId() {
        return organisationId;
    }
}
