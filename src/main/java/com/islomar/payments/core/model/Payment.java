package com.islomar.payments.core.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String id;
    private String type;
    private String organisationId;
    private Map<String, Object> attributes;

    public Payment(String id) {
        this.id = id;
    }
}
