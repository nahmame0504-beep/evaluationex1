package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    public static void main(String[] args) {
        // Désactiver les logs d'information d'Hibernate (le texte rouge) pour n'avoir que notre affichage
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cmdService = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // 1. Création des catégories
        Categorie cat1 = new Categorie("C1", "Informatique");
        Categorie cat2 = new Categorie("C2", "Bureautique");
        cs.create(cat1);
        cs.create(cat2);

        // 2. Création des produits
        Produit p1 = new Produit("ES12", 120, cat1);
        Produit p2 = new Produit("ZR85", 100, cat1);
        Produit p3 = new Produit("EE85", 200, cat2);
        // Produit supplémentaire pour tester d'autres méthodes si besoin
        Produit p4 = new Produit("XX10", 50, cat2);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);
        ps.create(p4);

        // 3. Création de la commande
        // Date: 14 Mars 2013 (l'année commence à 1900, le mois commence à 0)
        Commande cmd = new Commande(new Date(113, 2, 14));
        cmdService.create(cmd);

        // 4. Ajouter les lignes de commande
        lcs.create(new LigneCommande(p1, cmd, 7));
        lcs.create(new LigneCommande(p2, cmd, 14));
        lcs.create(new LigneCommande(p3, cmd, 5));

        // ==== AFFICHAGE ATTENDU PAR L'EXERCICE ====
        System.out.println("\n========================================================");
        ps.afficherProduitsParCommande(cmd);
        System.out.println("========================================================\n");

        // Fermer la session factory
        HibernateUtil.getSessionFactory().close();
    }
}
