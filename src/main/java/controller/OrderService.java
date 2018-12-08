package controller;

import DAO.OrderDao;
import pojo.Order;
import pojo.Product;
import pojo.User;

import java.util.List;

/**
 * Created by YR on 2018/12/4.
 */
public class OrderService {
    OrderDao service = new OrderDao();

    public boolean add(Product product, User user,String address,String phone){
        return service.add (product, user, address, phone);
    }

    public List<Order> blist(String uid){return service.blist (uid);}

    public List<Order> slist(String uid){return service.slist (uid);}

    public void delete(String oid){ service.delete (oid);}

    public void setState(String oid){service.setState (oid);}
}
