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
import java.util.Date;
import java.util.List;

/**
 * Created by jutal on 16-11-22.
 */
public class editDataServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = URLDecoder.decode(req.getParameter("content"), "UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        SummaryEntity summaryEntity = new SummaryEntity();
        Date date = new Date();
        summaryEntity.setId(id);
        summaryEntity.setDate(new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()));
        summaryEntity.setContent(content);

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.merge(summaryEntity);
        session.getTransaction().commit();

        Query q = session.createQuery("from SummaryEntity ");
        List<SummaryEntity> summaryEntityList = q.list();
        String jsonString = JSON.toJSONString(summaryEntityList);
        session.close();

        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.append(jsonString);
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
