package projet1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Commande {
    private Produit produit;
    private int quantite;

    public Commande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "produit=" + produit +
                ", quantite=" + quantite +
                '}';
    }
}

public class TraitementCommande {
    private Queue<Commande> fileCommandes;

    public TraitementCommande() {
        this.fileCommandes = new LinkedList<>();
    }

    public void passerCommande(GestionStocks gestionStocks) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez le nom du produit : ");
        String nomProduit = scanner.nextLine();

        System.out.print("Entrez la quantité souhaitée : ");
        int quantite = scanner.nextInt();
        scanner.nextLine();  // Pour consommer la nouvelle ligne après la quantité entière

        Produit produit = gestionStocks.rechercherProduitParNom(nomProduit);

        if (produit != null && produit.getQuantite() >= quantite) {
            Commande nouvelleCommande = new Commande(produit, quantite);
            fileCommandes.offer(nouvelleCommande);
            System.out.println("Commande ajoutée à la file d'attente.");
        } else {
            System.out.println("Produit non trouvé ou stock insuffisant. Veuillez vérifier le nom du produit.");
        }
    }

    public void traiterCommandes(GestionStocks gestionStocks) {
        while (!fileCommandes.isEmpty()) {
            Commande commandeEnCours = fileCommandes.poll();
            gestionStocks.mettreAJourStockApresVente(commandeEnCours.getProduit(), commandeEnCours.getQuantite());
            System.out.println("Commande traitée : " + commandeEnCours);
        }
    }
}