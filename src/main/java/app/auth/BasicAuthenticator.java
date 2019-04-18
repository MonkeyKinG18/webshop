package app.auth;

import dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import model.User;

import javax.inject.Inject;
import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User>
{
    private final UserDao userDao;

    @Inject
    public BasicAuthenticator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        User user = userDao.getByEmailAddress(credentials.getUsername());

        if (user != null && user.getPassword().equals(credentials.getPassword()))
        {
            return Optional.of(user);
        }

        return Optional.empty();
    }
}
