package dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import model.User;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao extends AbstractDAO<User> {

    @Inject
    public UserDao(SessionFactory factory) {
        super(factory);
    }

    public List<User> retrieveAll() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        criteria.from(User.class);
        return currentSession().createQuery(criteria).getResultList();
    }

    public User getByEmailAddress(String emailAdress) {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> user = cr.from(User.class);
        cr.select(user).where(cb.equal(cb.lower(user.get("emailAddress")), emailAdress.toLowerCase()));
        return currentSession().createQuery(cr).uniqueResult();
    }

    public void store(User user) {
        persist(user);
    }

    public void update(User user) {
        currentSession().merge(user);
    }

    public void delete(int id) {
        User user = currentSession().load(User.class, id);
        currentSession().delete(user);
    }

    public Integer retrieveHighestId() {
        Integer id = (Integer) currentSession().createQuery("select max(user_id) from user").uniqueResult();
        return id == null ? 1 : id;
    }

    public User retrieve(Integer id) {
        return get(id);
    }


}