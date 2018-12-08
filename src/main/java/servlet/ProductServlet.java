package servlet;

import DAO.OrderDao;
import DAO.UserDao;
import controller.ProductService;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.BeanUtils;
import pojo.Product;
import DAO.ProductDao;
import pojo.User;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by YR on 2018/12/3.
 */
public class ProductServlet extends BaseServlet {

    private ProductService service = new ProductService ();

    protected void find(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String pid = request.getParameter("pid");
        Product product = service.findProduct (pid);
        JSONArray data = JSONArray.fromObject(product);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }

    public void buyservice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter ("pid");
        int num = Integer.parseInt(request.getParameter ("num"));
        String address = request.getParameter ("address");
        String phone = request.getParameter ("phone");
        String name = (String)request.getSession().getAttribute("userName");
        User u = new UserDao ().findUser (name);
        Product p = new ProductDao ().findProduct (pid);
        if(null==u){
            response.sendRedirect("/login.html");
        }
        else{
            OrderDao orderDao = new OrderDao ();
            service.buy (pid,num);
            orderDao.add (p,u,address,phone);
        }
    }

    public void deleteservice(HttpServletRequest request, HttpServletResponse response){
        String pid = request.getParameter ("pid");
        service.delete (pid);
    }

    public void show(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pid = request.getParameter ("pid");
        List<Product> products = service.ownProduct (pid);
        JSONArray data = JSONArray.fromObject(products);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }

    public void category(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cid = request.getParameter ("cid");
        List<Product> products = service.clist(cid);
        JSONArray data = JSONArray.fromObject(products);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }
}
