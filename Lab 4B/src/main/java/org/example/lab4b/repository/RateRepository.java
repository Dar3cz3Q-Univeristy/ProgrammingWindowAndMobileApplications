package org.example.lab4b.Repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.lab4b.Model.Rate;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class RateRepository {

    public Rate save(Rate rate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(rate);
            session.getTransaction().commit();
            return rate;
        }
    }

    @SuppressWarnings("unchecked")
    public List<Rate> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Rate").getResultList();
        }
    }

    public List<Object[]> getStatistics() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
            Root<TeacherClass> groupRoot = query.from(TeacherClass.class);
            Join<TeacherClass, Rate> rateJoin = groupRoot.join("rates");

            query.multiselect(
                    groupRoot.get("name"),
                    cb.count(rateJoin.get("id")),
                    cb.avg(rateJoin.get("rate"))
            ).groupBy(groupRoot.get("name"));

            return session.createQuery(query).list();
        }
    }
}
