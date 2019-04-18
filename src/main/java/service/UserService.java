package service;

import dao.UserDao;
import model.User;


import javax.inject.Inject;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserDao dao;

    @Inject
    public UserService(UserDao dao){
        this.dao = dao;
    }

    public List<User> retrieveAll() {
        return dao.retrieveAll();
    }

    public void store(User user) {

        System.out.println(user.getName().equals("Omid"));
        user.setRoles("GUEST");
//        user.setUserId(generateNewId());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        if(user.getName().equals("Omid")){
            user.setRoles("ADMIN");
        }
        dao.store(user);
    }

    public Integer generateNewId() {
        int id = dao.retrieveHighestId();
        if(id > (id * 1000)){
            id += 1;
        }else{
            id = (id * 1000)+1;
        }
        return id;
    }

    public void delete(int id) {
        dao.delete(id);
    }

    public void update(User user) {
        dao.update(user);
    }

    public User retrieve(String emailadress) {
        return dao.getByEmailAddress(emailadress);
    }
    public User retrieveById(Integer id) {
        return dao.retrieve(id);
    }


}
