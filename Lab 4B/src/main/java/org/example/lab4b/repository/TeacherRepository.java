package org.example.lab4b.Repository;

import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class TeacherRepository {
    public Teacher save(Teacher teacher) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.persist(teacher);
            session.getTransaction().commit();
            return teacher;
        }
    }

    public Teacher update(Teacher teacher) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.merge(teacher);
            session.getTransaction().commit();
            return teacher;
        }
    }

    public void delete(Teacher teacher) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.remove(teacher);
            session.getTransaction().commit();
        }
    }

    public Teacher findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Teacher.class, id);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Teacher> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Teacher").getResultList();
        }
    }

    public List<Teacher> findByGroup(String group) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT t FROM Teacher t WHERE t.group.name = :groupName"
            ).setParameter("groupName", group).getResultList();
        }
    }

    public int countByGroup(String group) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long count = (Long) session.createQuery(
                    "SELECT COUNT(t) FROM Teacher t WHERE t.group.name = :groupName"
            ).setParameter("groupName", group).uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }
}
