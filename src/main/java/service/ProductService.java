package service;

import com.google.inject.Inject;
import dao.ProductDao;
import model.Product;
import model.User;

import java.util.List;

public class ProductService {
    private ProductDao productDao;

    @Inject
    public ProductService(ProductDao productDao){
        this.productDao = productDao;

    }

    public List<Product> retrieveAll(){
        return productDao.retrieveAll();
    }

    public void store(Product product) {
        productDao.store(product);
    }

    public Product retrieveProduct(Integer id) {
        return productDao.retrieve(id);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public void delete(int id) {
        productDao.delete(id);
    }


}
