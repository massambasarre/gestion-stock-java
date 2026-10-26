package projet1;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Collectors;
public class GestionStocks {
    private Stack<Produit> produitsNonPerissables;
    private Queue<Produit> produitsPerissables;
    private List<Produit> stock;

    public GestionStocks() {
        this.produitsNonPerissables = new Stack<>();
        this.produitsPerissables = new LinkedList<>();
        this.stock = new ArrayList<>();

        // Ajout d'au moins un produit de chaque type lors de la création du stock
        Produit produitNonPerissable = new Produit("ProduitNonPerissable", 10, 5.0, "");
        produitsNonPerissables.push(produitNonPerissable);
       stock.add(produitNonPerissable);

        Produit produitPerissable = new Produit("ProduitPerissable", 5, 7.0, "2024-12-31");
        produitsPerissables.offer(produitPerissable);
      stock.add(produitPerissable);
    }

    public void ajouterProduitAuStock(Produit produit) {
        // Vérifier si un produit avec le même nom existe déjà dans le stock
        for (Produit p : stock) {
            if (p.getNom().equalsIgnoreCase(produit.getNom())) {
                // Produit avec le même nom trouvé, mettre à jour la quantité
                p.setQuantite(p.getQuantite() + produit.getQuantite());
                System.out.println("La quantité du produit existant a été mise à jour.");
                return;
            }
        }

        // Si le produit n'existe pas, l'ajouter au stock
        if (produit.estPerissable()) {
            produitsPerissables.offer(produit);
        } else {
            produitsNonPerissables.push(produit);
        }
        stock.add(produit);
        System.out.println("Le produit a été ajouté au stock avec succès.");
    }



 // Méthode pour afficher le stock de produits non périssables
    public void afficherStockNonPerissables() {
        List<Produit> stockNonPerissables = produitsNonPerissables.stream().collect(Collectors.toList());
        System.out.println("\nAffichage du stock de produits non périssables :");

        if (stockNonPerissables.isEmpty()) {
            System.out.println("Le stock de produits non périssables est vide.");
        } else {
            for (Produit produit : stockNonPerissables) {
                System.out.println(produit);
            }
        }
    }

    public void afficherStockPerissables() {
        List<Produit> stockPerissables = produitsPerissables.stream().collect(Collectors.toList());
        System.out.println("\nAffichage du stock de produits périssables :");

        if (stockPerissables.isEmpty()) {
            System.out.println("Le stock de produits périssables est vide.");
        } else {
            for (Produit produit : stockPerissables) {
                System.out.println(produit);
            }
        }
    }


    // Méthode pour rechercher un produit par nom
    public Produit rechercherProduitParNom(String nomProduit) {
        for (Produit produit : produitsNonPerissables) {
            if (produit.getNom().equalsIgnoreCase(nomProduit)) {
                return produit;
            }
        }

        for (Produit produit : produitsPerissables) {
            if (produit.getNom().equalsIgnoreCase(nomProduit)) {
                return produit;
            }
        }

        return null; // Produit non trouvé
    }

    // Méthode pour vérifier la disponibilité d'un produit en fonction de la quantité demandée
    public boolean verifierDisponibilite(Produit produit, int quantite) {
        if (produitsNonPerissables.contains(produit)) {
            return produitsNonPerissables.size() >= quantite;
        } else if (produitsPerissables.contains(produit)) {
            return produitsPerissables.size() >= quantite;
        }

        return false; // Produit non trouvé
    }

    public void mettreAJourStockApresVente(Produit produit, int quantiteVendue) {
        if (produit != null) {
            int nouvelleQuantite = produit.getQuantite() - quantiteVendue;
            produit.setQuantite(nouvelleQuantite);
            System.out.println("Stock mis à jour après la vente.");
        } else {
            System.out.println("Produit non trouvé. Mise à jour du stock impossible.");
        
       }
    }
        
    public void genererRapportProduitsEnRuptureStock(GestionStocks gestionStocks) {
        System.out.println("\nRapport des produits en rupture de stock :");
        List<Produit> produitsEnRuptureStock = gestionStocks.getProduitsEnRuptureStock();
        if (produitsEnRuptureStock.isEmpty()) {
            System.out.println("Aucun produit n'est en rupture de stock.");
        } else {
            for (Produit produit : produitsEnRuptureStock) {
                System.out.println(produit);
            }
        }
    }
    
    // Méthode pour obtenir la liste de tous les produits en stock
    public List<Produit> getStock() {
        List<Produit> stock = new ArrayList<>();
        
        // Ajouter tous les produits non périssables
        stock.addAll(produitsNonPerissables);

        // Ajouter tous les produits périssables
        stock.addAll(produitsPerissables);

        return stock;
    }

    public List<Produit> getProduitsEnRuptureStock() {
        return stock.stream()
            .filter(produit -> produit.getQuantite() == 0)
            .collect(Collectors.toList());
    }
}



