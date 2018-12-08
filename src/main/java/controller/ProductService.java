package controller;

import DAO.ProductDao;
import pojo.Product;

import java.util.List;

/**
 * Created by YR on 2018/12/1.
 */
public class ProductService {
    ProductDao productDao = new ProductDao ();

    public boolean sell(String cid, String uid, String name, String image, double price, int num, String pdesc){
        return productDao.sell (cid, uid, name, image, price, num, pdesc);
    }

    public boolean buy(String pid,int n){
        return productDao.buy (pid,n);
    }

    public void delete(String pid){ productDao.delete (pid); }

    public Product findProduct(String pid){return productDao.findProduct (pid);}

    public Product findByName(String name){return productDao.findByName (name);}

    public List<Product> ownProduct(String uid){return productDao.listByseller (uid);}

    public List<Product> clist(String cid){return productDao.clist (cid);}
}
