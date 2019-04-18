package service;

import dao.InfoDao;
import model.Info;


import javax.inject.Inject;
import java.util.List;

public class InfoService {
    private InfoDao dao;

    @Inject
    public InfoService(InfoDao dao){
        this.dao = dao;
    }

    public List<Info> retrieveAll() {
        return dao.retrieveAll();
    }

    public void store(Info info) {
        dao.store(info);
    }

}
