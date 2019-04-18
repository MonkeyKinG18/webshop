package dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import model.Orders;
import model.Product;
import model.User;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class ProductDao extends AbstractDAO<Product> {

    @Inject
    public ProductDao(SessionFactory factory){
        super(factory);
    }

    public List<Product> retrieveAll(){
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        criteria.from(Product.class);
        return currentSession().createQuery(criteria).getResultList();
    }

    public Product retrieve(Integer id) {
        return get(id);
    }

    public void store(Product product) {
        persist(product);
    }

    public void update(Product product) {
        currentSession().merge(product);
    }

    public void delete(int id) {
        Product product = currentSession().load(Product.class, id);
        currentSession().delete(product);
    }



}
