package app;

import com.google.common.collect.ImmutableList;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;
import org.reflections.Reflections;

import javax.persistence.Entity;

public class WebshopHibernateBundle extends HibernateBundle<MyConfiguration> implements ConfiguredBundle<MyConfiguration> {

        public WebshopHibernateBundle() {
            super(getEntities(), new SessionFactoryFactory());
        }

        private static ImmutableList<Class<?>> getEntities() {
            Reflections reflections = new Reflections("model");
            ImmutableList<Class<?>> entities = ImmutableList.copyOf(reflections.getTypesAnnotatedWith(Entity.class));
            return entities;
        }

        @Override
        public DataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    }

