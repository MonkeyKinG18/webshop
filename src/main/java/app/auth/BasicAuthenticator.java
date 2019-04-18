package app.auth;

import dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class BasicAuthenticator implements Authenticator<BasicCredentials, User>, Authorizer<User>
{
    private final UserDao userDao;

    @Inject
    public BasicAuthenticator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        User user = userDao.getByEmailAddress(credentials.getUsername());
        System.out.println(credentials.getUsername());
        if (user != null && BCrypt.checkpw(credentials.getPassword(), user.getPassword()))
        {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public boolean authorize(User user, String roleName)
    {
        return user.hasRole(roleName);
    }
}
