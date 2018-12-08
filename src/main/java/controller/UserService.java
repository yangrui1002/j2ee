package controller;

import DAO.UserDao;
/**
 * Created by YR on 2018/11/30.
 */

public class UserService {

    UserDao dao = new UserDao();
    //注册
    public boolean register(String name, String password, String phone) {
        return dao.register(name, password, phone);
    }
    //登录
    public boolean login(String name, String password) {
        return dao.login(name, password);
    }
}
