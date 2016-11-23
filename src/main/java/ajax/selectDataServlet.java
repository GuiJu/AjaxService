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
import java.util.List;

/**
 * Created by jutal on 16-11-23.
 */
public class selectDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = URLDecoder.decode(req.getParameter("content"), "UTF-8");
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query;
        if (content.length() == 0) {
            query = session.createQuery("from SummaryEntity");
        } else {
            //%(百分号):匹配任意类型、任意长度的字符串
            query = session.createQuery("from SummaryEntity as S where S.content like '%" + content + "%'");
        }
        List<SummaryEntity> summaryEntityList = query.list();
        String jsonString = JSON.toJSONString(summaryEntityList);
        session.getTransaction().commit();
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
