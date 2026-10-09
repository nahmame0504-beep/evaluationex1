package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService implements IDao<Produit> {
    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit p = session.get(Produit.class, id);
        session.close();
        return p;
    }

    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = session.createQuery("from Produit").list();
        session.close();
        return list;
    }

    public void afficherProduitsParCategorie(Categorie c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("from Produit p where p.categorie = :cat", Produit.class)
                .setParameter("cat", c).list();
        System.out.println("Produits de la catégorie: " + c.getLibelle());
        for (Produit p : produits) {
            System.out.println("- " + p.getReference() + " : " + p.getPrix() + " DH");
        }
        session.close();
    }

    public void afficherProduitsCommandesEntreDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery(
                "select distinct lc.produit from LigneCommande lc where lc.commande.date between :d1 and :d2", Produit.class)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .list();
        System.out.println("Produits commandés entre " + d1 + " et " + d2 + " :");
        for(Produit p : produits) {
             System.out.println(p.getReference());
        }
        session.close();
    }

    public void afficherProduitsParCommande(Commande cmd) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        String dateStr = sdf.format(cmd.getDate());
        // Mettre la première lettre du mois en majuscule si nécessaire (ex: "Mars" au lieu de "mars")
        String[] dateParts = dateStr.split(" ");
        if(dateParts.length == 3) {
            dateParts[1] = dateParts[1].substring(0, 1).toUpperCase() + dateParts[1].substring(1);
            dateStr = dateParts[0] + " " + dateParts[1] + " " + dateParts[2];
        }

        System.out.println("Commande : " + cmd.getId() + "     Date : " + dateStr);
        System.out.println("Liste des produits :");
        System.out.printf("%-12s%-10s%-10s%n", "Référence", "Prix", "Quantité");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<ma.projet.classes.LigneCommande> lignes = session.createQuery(
                "from LigneCommande lc where lc.commande = :cmd", ma.projet.classes.LigneCommande.class)
                .setParameter("cmd", cmd)
                .list();
                
        for (ma.projet.classes.LigneCommande lc : lignes) {
            Produit p = lc.getProduit();
            String prixFmt = (p.getPrix() == (long)p.getPrix()) ? 
                             String.format("%d DH", (long)p.getPrix()) : 
                             String.format("%.2f DH", p.getPrix());
            System.out.printf("%-12s%-10s%-10d%n", p.getReference(), prixFmt, lc.getQuantite());
        }
        session.close();
    }

    public void afficherProduitsPrixSuperieurA100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> query = session.createNamedQuery("Produit.findByPrixGreaterThan", Produit.class);
        query.setParameter("prix", 100.0);
        List<Produit> produits = query.list();
        
        System.out.println("Produits dont le prix est supérieur à 100 DH :");
        for (Produit p : produits) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }
        session.close();
    }
}
