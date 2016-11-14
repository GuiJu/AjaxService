package ajax;

import com.alibaba.fastjson.JSON;
import dao.HibernateUtil;
import domain.SummaryEntity;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by jutal on 16-11-12.
 */
public class getDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query q = session.createQuery("from SummaryEntity");
        List<SummaryEntity> summaryEntityList = q.list();
        session.getTransaction().commit();
        session.close();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        String jsonString = JSON.toJSONString(summaryEntityList);
        out.append(jsonString);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*以下为使用xml格式传送数据*/
        /*resp.setContentType("text/xml");
        out.println("<SummaryList>");
        for (int i = 0; i < summaryEntityList.size(); i++) {
            out.println("<Summary>");
            out.println("<date>" + summaryEntityList.get(i).getDate() + "</date>");
            out.println("<content>" + summaryEntityList.get(i).getContent() + "</content>");
        }
        out.println("</SummaryList>");
        out.flush();
        out.close();*/
    }
}
