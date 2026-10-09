package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class CategorieService implements IDao<Categorie> {
    @Override
    public boolean create(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Categorie findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categorie c = session.get(Categorie.class, id);
        session.close();
        return c;
    }

    @Override
    public List<Categorie> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> list = session.createQuery("from Categorie").list();
        session.close();
        return list;
    }
}
