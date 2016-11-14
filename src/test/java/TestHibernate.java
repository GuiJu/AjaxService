import com.alibaba.fastjson.JSON;
import dao.HibernateUtil;
import domain.SummaryEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by jutal on 16-11-11.
 */
public class TestHibernate {
    @Test
    public void TestHibernate() {
        final Session session = HibernateUtil.getSession();
        session.beginTransaction();
        SummaryEntity data = new SummaryEntity();
        data.setContent("TestHibernate");
        java.util.Date utilDate = new java.util.Date();
        System.out.println(utilDate.toString());
        data.setDate(new Date(utilDate.getYear(), utilDate.getMonth(), utilDate.getDate()));
        session.save(data);
        session.getTransaction().commit();
        session.close();
        System.out.println("Success.");
    }

    @Test
    public void TestGetAll() {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query q = session.createQuery("from SummaryEntity");
        List<SummaryEntity> summaryEntityList = q.list();
        session.getTransaction().commit();
        session.close();

        String jsonString = JSON.toJSONString(summaryEntityList);
        System.out.println(jsonString);
    }

}
