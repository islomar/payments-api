package com.islomar.payments.api;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ApiResponseLinks {

    private Map<String, URL> links;

    public ApiResponseLinks() {
        this.links = new HashMap<>();
    }

    public void addLink(String linkName, URL linkUri) {
        links.put(linkName, linkUri);
    }

    public Map<String, URL> getLinks() {
        return Collections.unmodifiableMap(this.links);
    }
}
