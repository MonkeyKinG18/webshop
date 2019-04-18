package service;

import com.google.inject.Inject;
import dao.OrderDao;
import dao.UserDao;
import model.Orders;
import model.User;

import java.util.List;


public class OrderService {

    private OrderDao dao;

    @Inject
    public OrderService(OrderDao dao){
        this.dao = dao;
    }

    public Orders retrieve(int id) {
        return dao.retrieve(id);
    }

    public List<Orders> retrieveAll() {
        return dao.retrieveAll();
    }

    public void store(Orders order) {
        System.out.println(order.getUser().getEmailAddress());
        dao.store(order);
    }

    public void update(Orders order){
        dao.update(order);
    }

    public void delete(int id) {
        dao.delete(id);
    }




}
