package servlet;

import controller.UserService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by YR on 2018/11/30.
 */

public class LoginServlet extends HttpServlet{
    UserService service = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if("admin".equals (userName) && "123456".equals (password)){
            request.getSession().setAttribute("userName", userName);
            request.getRequestDispatcher("/user.html").forward(request, response);
        }
        else{
            boolean flag = service.login(userName, password);
            if (flag == false) {
                request.getRequestDispatcher("/login.html").forward(request, response);
            } else {
                request.getSession().setAttribute("userName", userName);
                request.getRequestDispatcher("/index.html").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
