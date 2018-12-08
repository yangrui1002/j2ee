package servlet;

import DAO.UserDao;
import controller.ProductService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import pojo.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by YR on 2018/12/6.
 */
public class addServlet extends HttpServlet {

    ProductService service = new ProductService ();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            //获得磁盘文件工厂对象：
            DiskFileItemFactory dfif = new DiskFileItemFactory();
            //设置缓存区的大小
            dfif.setSizeThreshold(3 * 1024 * 1024); //不超过3M，不会产生临时文件
            //通过工厂获得核心解析类
            ServletFileUpload fileUpload = new ServletFileUpload(dfif);
            fileUpload.setHeaderEncoding("UTF-8"); //解决中文文件乱码问题
            //解析request对象，返回List集合，集合中的内容是分割线分成的每个部分
            List<FileItem> list = fileUpload.parseRequest(request);
            Map<String,String> map = new LinkedHashMap<String, String> ();
            //遍历每个部分
            String path = "E:\\javaweb\\src\\main\\webapp\\products\\1";
            String filename = UUID.randomUUID().toString().replaceAll("-","") + ".jpg";
            for (FileItem fileItem : list) {
                if(fileItem.isFormField()){
                    //普通项
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString("utf-8"); //解决普通项乱码问题
                    map.put(name, value);
                }else{
                    //文件上传项
                    File f = new File(path, filename);
                    f.getParentFile().mkdirs();
                    // 通过item.getInputStream()获取浏览器上传的文件的输入流
                    InputStream is = fileItem.getInputStream();
                    // 复制文件
                    FileOutputStream fos = new FileOutputStream(f);
                    byte b[] = new byte[3 * 1024 * 1024];
                    int length = 0;
                    while (-1 != (length = is.read(b))) {
                        fos.write(b, 0, length);
                    }
                    fos.close();
                }
            }
            Product product = new Product();
            BeanUtils.populate(product, map);

            String userName = (String) request.getSession().getAttribute("userName");
            String uid = new UserDao ().findUser (userName).getId ();
            product.setUid (uid);
            product.setId (filename);
            String src = "/products/1/" + filename ;
            PrintWriter pw= response.getWriter();
            boolean flag = service.sell (product.getCid (),uid,product.getPname(),src,product.getShop_price (),product.getNum (),product.getPdesc ());
            if (flag == false) {
                pw.println("<span>上传数据失败！</span>");
            } else {
                pw.println("<span>商品上架成功！</span>");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
