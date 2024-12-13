package org.example.lab4b.Repository;

import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class TeacherClassRepository {

    public TeacherClass save(TeacherClass group) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(group);
            session.getTransaction().commit();
            return group;
        }
    }

    public void delete(TeacherClass group) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.remove(group);
            session.getTransaction().commit();
        }
    }

    public TeacherClass findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(TeacherClass.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<TeacherClass> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM TeacherClass").getResultList();
        }
    }

    public TeacherClass findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (TeacherClass) session.createQuery(
                    "FROM TeacherClass WHERE name=:name"
            ).setParameter("name", name).uniqueResult();
        }
    }
}
