package projet1;
import projet1.GestionVentes.Vente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GestionRapports {

    private GestionVentes gestionVentes;
    private GestionProduits gestionProduits;

    public GestionRapports(GestionVentes gestionVentes, GestionProduits gestionProduits) {
        this.gestionVentes = gestionVentes;
        this.gestionProduits = gestionProduits;
    }

    public void genererRapportVentes() {
        System.out.println("\nRapport des ventes :");
        double totalVentes = gestionVentes.getTotalSales();
        System.out.printf("Total sales : %.2f%n", totalVentes);
    }

    public void genererRapportProduitsPlusVendus(int n) {
        List<Vente> meilleuresVentes = gestionVentes.getMeilleuresVentes(n);
        if (meilleuresVentes.size() > 0) {
            System.out.println("\nRapport des produits les plus vendus :");
            for (Vente vente : meilleuresVentes) {
                Produit produit = vente.getProduit();
                System.out.printf("%s - Quantité vendue : %d - Prix total : %.2f%n", produit.getNom(), vente.getQuantiteVendue(), vente.getPrixTotal());
            }
        } else {
            System.out.println("\nAucune vente enregistrée.");
        }
    }

    public void genererRapportProduitsEnRuptureStock(GestionStocks gestionStocks) {
        System.out.println("\nRapport des produits en rupture de stock :");
        List<Produit> produitsEnRuptureStock = gestionStocks.getProduitsEnRuptureStock();
        for (Produit produit : produitsEnRuptureStock) {
            System.out.println(produit);
        }
    }
}
