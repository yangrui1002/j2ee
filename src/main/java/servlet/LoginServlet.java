package servlet;

import controller.UserService;
import util.mailutil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by YR on 2018/11/30.
 */

public class LoginServlet extends BaseServlet{
    UserService service = new UserService();
    private String code1="";
    private String emil="";

    public void sign(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if("admin".equals (userName) && "123456".equals (password)){
            request.getSession().setAttribute("userName", userName);
            request.getRequestDispatcher("/user.htm").forward(request, response);
        }
        else{
            boolean flag = service.login(userName, password);
            if (flag == false) {
                request.getRequestDispatcher("/login.htm").forward(request, response);
            } else {
                request.getSession().setAttribute("userName", userName);
                request.getRequestDispatcher("/index.htm").forward(request, response);
            }
        }
    }

    public void sendemil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        emil = request.getParameter ("emil");
        if(service.find (emil)==null){
            PrintWriter re = response.getWriter();
            re.print ("<font color='red'>该邮箱不存在</font>");
        }
        else {
            Random random = new Random ();
            for (int i = 0; i < 6; i++) {
                int r = random.nextInt(10);
                code1 = code1 + r;
            }
            String content = "用户，你好：此次重置交易网密码的验证码为 ";
            content += code1;
            content += "如果你没有进行相关操作，错误收到此邮件，你无需执行任何操作，你的密码将不会被修改";
            try{
                mailutil.sendEmail (emil,"交易网",content);
            }catch (Exception e){
                e.printStackTrace ();
            }
            request.getRequestDispatcher("/reset2.htm").forward(request, response);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter ("code");
        if(code1.equals (code)){
            String password = request.getParameter ("password");
            boolean flag = service.update (emil,password);
            if (flag==true){
                request.getRequestDispatcher("/login.htm").forward(request, response);
            }
        }
        else {
            PrintWriter re = response.getWriter();
            re.print ("<font color='red'>密码重置失败</font>");
        }
    }
}
