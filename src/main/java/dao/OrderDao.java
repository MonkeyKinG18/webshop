package dao;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import model.Orders;
import model.OrderItem;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;


public class OrderDao extends AbstractDAO<Orders> {

    @Inject
    public OrderDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Orders retrieve(Integer id) {
        return get(id);
    }

    public List<Orders> retrieveAll() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Orders> criteria = builder.createQuery(Orders.class);
        criteria.from(Orders.class);
        return currentSession().createQuery(criteria).getResultList();
    }

    public void store(Orders order) {
        for (OrderItem orderItem: order.getOrderItemsList()) {
            orderItem.setOrders(order);
        }
        persist(order);
    }

    public void update(Orders order) {
        for (OrderItem orderItem: order.getOrderItemsList()) {
            orderItem.setOrders(order);
        }
        currentSession().merge(order);
    }

    public void delete(int id) {
        Orders order = currentSession().load(Orders.class, id);
        currentSession().delete(order);
    }
}
