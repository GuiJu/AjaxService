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
import java.net.URLDecoder;
import java.sql.Date;

/**
 * Created by jutal on 16-11-16.
 */
public class addDataServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = URLDecoder.decode(req.getParameter("content"), "UTF-8");

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        SummaryEntity summaryEntity = new SummaryEntity();
        java.util.Date date = new java.util.Date();
        summaryEntity.setDate(new Date(date.getYear(), date.getMonth(), date.getDate()));
        summaryEntity.setContent(content);
        session.save(summaryEntity);
        session.getTransaction().commit();

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        Query query = session.createQuery("from SummaryEntity ");
        String jsonString = JSON.toJSONString(query.list());
        out.append(jsonString);
        out.flush();

        session.close();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
