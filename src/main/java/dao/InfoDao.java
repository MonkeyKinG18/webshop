package dao;

import io.dropwizard.hibernate.AbstractDAO;
import model.Info;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class InfoDao extends AbstractDAO<Info> {

    public InfoDao(SessionFactory factory) {
        super(factory);
    }

    public List<Info> findAll() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Info> criteria = builder.createQuery(Info.class);
        criteria.from(Info.class);
        return currentSession().createQuery(criteria).getResultList();
    }
}