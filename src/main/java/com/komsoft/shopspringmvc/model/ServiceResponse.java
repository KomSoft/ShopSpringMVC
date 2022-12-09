package com.komsoft.shopspringmvc.model;

public class ServiceResponse {

    private final String url;
    private final String message;

    public ServiceResponse(String url, String message) {
        this.url = url;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
