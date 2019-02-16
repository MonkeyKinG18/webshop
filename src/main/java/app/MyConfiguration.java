package app;

import io.dropwizard.Configuration;

public class MyConfiguration extends Configuration {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}