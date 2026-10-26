package projet1;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestionVentes {
    // Classe interne pour représenter une vente
	public static class Vente {
        private Produit produit;
        private double prixTotal;
        private int quantiteVendue;

        public Vente(Produit produit, int quantiteVendue, double prixTotal) {
            this.produit = produit;
            this.prixTotal = prixTotal;
            this.quantiteVendue = quantiteVendue;
        }
        public int getQuantiteVendue() {
            return quantiteVendue;
        }
        public double getPrixTotal() {
            return prixTotal;
        }
        public Produit getProduit() {
            return produit;
        }
        

        @Override
        public String toString() {
            return "Vente{" +
                    "produit=" + produit +
                    ", quantiteVendue=" + quantiteVendue +
                    ", prixTotal=" + prixTotal +
                    '}';
        }
       
    }

    private List<Vente> ventes; // Liste pour stocker les ventes

    public GestionVentes() {
        this.ventes = new ArrayList<>();
    }
    
    public double getTotalSales() {
        return ventes.stream()
            .mapToDouble(vente -> vente.getPrixTotal())
            .sum();
    }


    // Méthode pour enregistrer une vente
    public void enregistrerVente(Produit produit, int quantite, double prixTotal) {
        Vente nouvelleVente = new Vente(produit, quantite, prixTotal);
        ventes.add(nouvelleVente);
        System.out.println("Vente enregistrée avec succès.");
    }

    // Méthode pour afficher toutes les ventes
    public void afficherVentes() {
        if (ventes.isEmpty()) {
            System.out.println("Aucune vente enregistrée.");
        } else {
            System.out.println("\nAffichage des ventes :");
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        }
    }
    
    public List<Vente> getMeilleuresVentes(int n) {
        List<Vente> meilleuresVentes = new ArrayList<>();
        if (ventes.size() > 0) {
            meilleuresVentes = ventes.stream()
                .sorted((v1, v2) -> Double.compare(v2.getPrixTotal(), v1.getPrixTotal()))
                .limit(n)
                .collect(Collectors.toList());
        }
        return meilleuresVentes;
    }
    
    public void genererRapportVentes() {
        System.out.println("\nRapport des ventes :");
        double totalSales = ventes.stream()
            .mapToDouble(Vente::getPrixTotal)
            .sum();
        System.out.printf("Total des ventes : %.2f%n", totalSales);
    }
}
