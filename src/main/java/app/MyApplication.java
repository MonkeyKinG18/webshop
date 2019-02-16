package app;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import recourse.MyResource;

public class MyApplication extends Application<MyConfiguration> {

    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    public void run(MyConfiguration myConfiguration, Environment environment) throws Exception {
        final MyResource resource = new MyResource();
        environment.jersey().register(resource);
        System.out.println("Value from dev.yml is "+myConfiguration.getUrl());
    }


}