package controller;

import DAO.UserDao;
import pojo.User;

/**
 * Created by YR on 2018/11/30.
 */

public class UserService {

    UserDao dao = new UserDao();
    //注册
    public boolean register(String name, String password, String emil) {
        return dao.register(name, password, emil);
    }
    //登录
    public boolean login(String name, String password) {
        return dao.login(name, password);
    }

    public boolean update(String emil, String password){
        return dao.update (emil, password);
    }

    public User find(String emil){
        return dao.findnyemil (emil);
    }
}
