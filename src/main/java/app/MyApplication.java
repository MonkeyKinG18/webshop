package app;

import app.auth.BasicAuthenticator;
import app.auth.DefaultUnauthorizedHandler;
import com.hubspot.dropwizard.guicier.GuiceBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import model.User;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Omid Wiar
 */

public class MyApplication extends Application<MyConfiguration> {

    public static final Locale DUTCH_LOCALE = new Locale("NL", "nl");

    private GuiceBundle<MyConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public String getName() {
        return "Albert API";
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        guiceBundle = GuiceBundle.defaultBuilder(MyConfiguration.class)
                .modules(new GuicierModule(bootstrap))
                .build();
        bootstrap.addBundle(guiceBundle);
//        bootstrap.addBundle(new AssetsBundle());
//        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(MyConfiguration configuration, Environment environment) {
        setupAuthentication(environment);
        configureCORS(environment);
//        environment.jersey().register(new PersistenceExceptionMapper());
//        environment.jersey().register(MultiPartFeature.class);
    }

   private void setupAuthentication(Environment environment)
    {
        BasicAuthenticator authenticationService = guiceBundle.getInjector().getInstance(BasicAuthenticator.class);
        DefaultUnauthorizedHandler unauthorizedHandler = guiceBundle.getInjector().getInstance(DefaultUnauthorizedHandler.class);

        environment.jersey().register(new AuthDynamicFeature(
            new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(authenticationService)
                .setAuthorizer(authenticationService)
                .setRealm("SUPER SECRET STUFF")
                .setUnauthorizedHandler(unauthorizedHandler)
                .buildAuthFilter())
        );

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }


    private void configureCORS(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }
}