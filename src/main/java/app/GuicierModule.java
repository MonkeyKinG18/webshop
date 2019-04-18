package app;

import app.auth.BasicAuthenticator;
import app.auth.DefaultUnauthorizedHandler;
import com.google.common.base.Preconditions;
import com.google.inject.Binder;
import com.google.inject.Provides;
import com.hubspot.dropwizard.guicier.DropwizardAwareModule;
import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.OrderResource;
import resource.ProductResource;
import resource.UserResource;
import service.OrderService;
import service.ProductService;
import service.UserService;

public class GuicierModule extends DropwizardAwareModule<MyConfiguration>
{
    private static final Logger logger = LoggerFactory.getLogger(GuicierModule.class);

    private final static WebshopHibernateBundle hibernateBundle = new WebshopHibernateBundle();

    public GuicierModule(Bootstrap<MyConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void configure(Binder binder) {
        bindResources(binder);
        bindDAOs(binder);
        bindServices(binder);
        bindMiscellaneous(binder);

    }

    private void bindMiscellaneous(Binder binder) {
        binder.bind(DefaultUnauthorizedHandler.class);

    }

    private void bindDAOs(Binder binder) {
        binder.bind(UserDao.class);
        binder.bind(ProductDao.class);
        binder.bind(OrderDao.class);

    }

    private void bindServices(Binder binder) {
        binder.bind(UserService.class);
        binder.bind(ProductService.class);
        binder.bind(OrderService.class);


    }

    private void bindResources(Binder binder) {
        binder.bind(UserResource.class);
        binder.bind(ProductResource.class);
        binder.bind(OrderResource.class);



    }
    @Provides
    public SessionFactory sessionFactory() {
        return Preconditions.checkNotNull(hibernateBundle.getSessionFactory());
    }

    @Provides
    public BasicAuthenticator basicAuthenticator() {
        UserDao dao = new UserDao(hibernateBundle.getSessionFactory());
        return new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(BasicAuthenticator.class, UserDao.class, dao);
    }
}
