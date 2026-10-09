package ma.projet.service;

import ma.projet.classes.LigneCommande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommande> {
    @Override
    public boolean create(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(LigneCommande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public LigneCommande findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        LigneCommande lc = session.get(LigneCommande.class, id);
        session.close();
        return lc;
    }

    @Override
    public List<LigneCommande> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> list = session.createQuery("from LigneCommande").list();
        session.close();
        return list;
    }
}
