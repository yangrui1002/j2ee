package servlet;

import controller.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by YR on 2018/11/30.
 */

public class RegisterServlet extends HttpServlet {
    UserService service = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String emil = request.getParameter ("emil");

        boolean flag = service.register(userName, password, emil);
        if (flag == false) {
            request.getRequestDispatcher("/register.htm").forward(request, response);
        } else {
            request.getRequestDispatcher("/login.htm").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

