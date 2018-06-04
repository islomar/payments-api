package com.islomar.payments.core.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

public class Payment {

    private String id;
    private String type;
    private String organisationId;
    private Map<String, Object> attributes;

    public Payment(){}

    public Payment(String id) {
        this.id = id;
    }

    public Payment(String id, String type, String organisationId, Map<String, Object> attributes) {
        this.id = id;
        this.type = type;
        this.organisationId = organisationId;
        this.attributes = attributes;
    }

    public String getId() {
        return this.id;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
