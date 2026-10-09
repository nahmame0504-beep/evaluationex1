package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    
    static {
        try {
            Properties properties = new Properties();
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            
            // Fallback si le dossier resources n'est pas dans le classpath (problème d'IDE classique)
            if (in == null) {
                File file = new File("src/main/resources/application.properties");
                if (file.exists()) {
                    in = new FileInputStream(file);
                } else {
                    throw new RuntimeException("Fichier application.properties introuvable !");
                }
            }
            
            properties.load(in);
            
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Categorie.class);
            configuration.addAnnotatedClass(Produit.class);
            configuration.addAnnotatedClass(Commande.class);
            configuration.addAnnotatedClass(LigneCommande.class);
            
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
