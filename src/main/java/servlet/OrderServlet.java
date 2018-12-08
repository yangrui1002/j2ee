package servlet;

import controller.OrderService;
import net.sf.json.JSONArray;
import pojo.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by YR on 2018/12/4.
 */
public class OrderServlet {

    public String changeState(HttpServletRequest req,HttpServletResponse resp){
        try{
            String oid = req.getParameter("oid");
            OrderService os = new OrderService ();
            os.setState(oid);
            return null;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected void sellservice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String uid = request.getParameter ("uid");
        List<Order> orders = new OrderService ().slist (uid);
        JSONArray data = JSONArray.fromObject(orders);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }

    protected void buyservice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String uid = request.getParameter ("uid");
        List<Order> orders = new OrderService ().blist (uid);
        JSONArray data = JSONArray.fromObject(orders);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }
}
