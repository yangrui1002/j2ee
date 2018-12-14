package servlet;

import controller.TopicService;
import net.sf.json.JSONArray;
import pojo.Topic;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by YR on 2018/12/14.
 */
public class TopicServlet extends BaseServlet{
    public void senttitle(HttpServletRequest req, HttpServletResponse resp){
        try{
            String title = req.getParameter("title");
            String uid = req.getParameter ("uid");
            String meassage = req.getParameter ("message");
            TopicService os = new TopicService ();
            os.senttopic (title,meassage,uid);

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void sentmessage(HttpServletRequest req, HttpServletResponse resp){
        try{
            String tid = req.getParameter("tid");
            String uid = req.getParameter ("uid");
            String meassage = req.getParameter ("message");
            TopicService os = new TopicService ();
            os.senttopic (tid,meassage,uid);

        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    protected void tlist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        List<Topic> titles = new TopicService ().tlist ();
        JSONArray data = JSONArray.fromObject(titles);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }

    protected void mlist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String tid = request.getParameter ("tid");
        List<Topic> messages = new TopicService ().mlist (tid);
        JSONArray data = JSONArray.fromObject(messages);
        response.setCharacterEncoding ("utf-8");
        PrintWriter re = response.getWriter();
        re.append (data.toString());
    }
}
