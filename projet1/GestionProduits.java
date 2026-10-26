package projet1;
import java.io.*;
import java.time.format.DateTimeParseException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Produit implements Serializable {
    // ... (votre code existant pour la classe Produit)
    private String nom;
    private int quantite;
    private double prixUnitaire;
    private LocalDate dateExpiration;

    public Produit(String nom, int quantite, double prixUnitaire, String dateExpiration) {
        this.nom = nom;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;

     // Validation et conversion de la date de type String à LocalDate
        if (!dateExpiration.isEmpty()) {
            try {
                this.dateExpiration = LocalDate.parse(dateExpiration, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                System.out.println("Format de date d'expiration incorrect. Utilisez le format 'yyyy-MM-dd'.");
                this.dateExpiration = null;  // Affecter null en cas d'erreur de format
            }
        }

     
    }
 // Méthode pour déterminer si le produit est périssable
    public boolean estPerissable() {
        return dateExpiration != null;
    }
    
    

    // Getters et Setters

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public LocalDate getDateExpiration() {
        return dateExpiration;
    }
    

    @Override
    public String toString() {
        return "Produit{" +
                "nom='" + nom + '\'' +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", dateExpiration=" + (dateExpiration != null ? dateExpiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "N/A") +
                '}';
    }
    }
    
    
public class GestionProduits {
    private List<Produit> produits;
    private static final String FICHIER_PRODUITS = "produits.ser";
    public GestionProduits() {
        
        this.produits = chargerProduits();
    }
    
    private void sauvegarderProduits() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FICHIER_PRODUITS))) {
            outputStream.writeObject(produits);
            System.out.println("Produits sauvegardés avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la sauvegarde des produits.");
        }
    }

    private List<Produit> chargerProduits() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FICHIER_PRODUITS))) {
            List<Produit> loadedProducts = (List<Produit>) inputStream.readObject();
            System.out.println("Produits chargés avec succès !");
            return loadedProducts;
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier de produits n'existe pas. Création d'une nouvelle liste.");
            // Création d'une nouvelle liste de produits
            return new ArrayList<>();
        } catch (EOFException e) {
            System.out.println("Le fichier de produits est vide. Création d'une nouvelle liste.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement des produits.");
        }
        return new ArrayList<>();
    }



    // Méthode pour ajouter un produit
    public void ajouterProduit() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAjout d'un nouveau produit :");

        System.out.print("Nom du produit : ");
        String nom = scanner.nextLine();

        System.out.print("Quantité disponible : ");
        int quantite = scanner.nextInt();

        System.out.print("Prix unitaire : ");
        double prixUnitaire = scanner.nextDouble();

        System.out.print("Date d'expiration : ");
        String dateExpiration = scanner.next();

        Produit nouveauProduit = new Produit(nom, quantite, prixUnitaire, dateExpiration);
        produits.add(nouveauProduit);

        System.out.println("Le produit a été ajouté avec succès.");
        sauvegarderProduits();
        System.out.println("Le produit a été ajouté avec succès et sauvegardé.");
        
    }
    

 // Méthode pour supprimer un produit
    public void supprimerProduit() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSuppression d'un produit :");

        System.out.print("Entrez le nom du produit à supprimer : ");
        String nomProduit = scanner.nextLine();

        // Vérifier si le produit existe avant de le supprimer
        boolean produitExiste = produits.stream().anyMatch(produit -> produit.getNom().equalsIgnoreCase(nomProduit));

        if (produitExiste) {
            produits.removeIf(produit -> produit.getNom().equalsIgnoreCase(nomProduit));
            System.out.println("Le produit a été supprimé avec succès.");
        } else {
            System.out.println("Le produit n'existe pas dans la liste. Veuillez vérifier le nom du produit.");
        }
    }


    // Méthode pour modifier la quantité d'un produit
    public void modifierQuantiteProduit() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nModification de la quantité d'un produit :");

        System.out.print("Entrez le nom du produit : ");
        String nomProduit = scanner.nextLine();

        for (Produit produit : produits) {
            if (produit.getNom().equalsIgnoreCase(nomProduit)) {
                System.out.print("Entrez la nouvelle quantité : ");
                int nouvelleQuantite = scanner.nextInt();

                produit.setQuantite(nouvelleQuantite);
                System.out.println("La quantité du produit a été modifiée avec succès.");
                return;
            }
        }

        System.out.println("Produit non trouvé dans la liste.");
    }

    // Méthode pour rechercher un produit
    public void rechercherProduit() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nRecherche d'un produit :");

        System.out.print("Entrez le nom du produit : ");
        String nomProduit = scanner.nextLine();

        for (Produit produit : produits) {
            if (produit.getNom().equalsIgnoreCase(nomProduit)) {
                System.out.println("Informations sur le produit trouvé :");
                System.out.println(produit);
                return;
            }
        }

        System.out.println("Produit non trouvé dans la liste.");
    }

    // Méthode pour afficher tous les produits
    public void afficherProduits() {
        System.out.println("\nAffichage de la liste des produits :");

        if (produits.isEmpty()) {
            System.out.println("La liste des produits est vide.");
        } else {
            for (Produit produit : produits) {
                System.out.println(produit);
            }
        }
    }
    //Nouvelle méthode pour obtenir la liste des produits
    public List<Produit> getProduits() {
        return produits;
    }
    
}

