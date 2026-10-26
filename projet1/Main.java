package projet1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        GestionProduits gestionProduits = new GestionProduits();
        GestionStocks gestionStocks = new GestionStocks();
        GestionVentes gestionVentes = new GestionVentes();
        TraitementCommande traitementCommande = new TraitementCommande();
        GestionRapports gestionRapports = new GestionRapports(gestionVentes, gestionProduits);
       
        int choix;
        int choixProduits = -1;

        do {
            System.out.println("\n\n------------------------------------------------------------------------------------");
            System.out.println("\tUQTR\t--- STRUCTURES DE DONNEES ET ALGORITHMES\t---DEVELOPPEMENT DE LOGICIELS");
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("\t\t\t\t |Khadidjaa|");
            System.out.println("\t\t\t\t Projet1| Java");
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println("----------------------------------  MENU  -------------------------------------------\n");
            System.out.println("1. Gestion des Produits");
            System.out.println("2. Gestion des Stocks");
            System.out.println("3. Traitement des Commandes");
            System.out.println("4. Gestion des Ventes");
            System.out.println("5. Gestion des Rapports");
            System.out.println("0. Quitter");
            System.out.println("--------------------------------------------------------------------------------------\n");
            System.out.print("Entrez votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine();  // Pour consommer la nouvelle ligne après le choix entier

            switch (choix) {
            case 1:
            	gestionProduitsMenu(scanner, gestionProduits,gestionStocks);

                // Si l'utilisateur est revenu au menu principal, sortir de la boucle principale
                if (choixProduits == 0) {
                    break;
                }
                
                   
                    
                    // Exécuter l'ajout de produit uniquement si l'utilisateur n'est pas revenu au menu principal
                    Produit produitAjoute = gestionProduits.getProduits().get(gestionProduits.getProduits().size() - 1);
                    gestionStocks.ajouterProduitAuStock(produitAjoute);
                    break;
               
                case 2:    
                    // Affichage du stock après l'ajout
                    gestionStocks.afficherStockNonPerissables();
                    gestionStocks.afficherStockPerissables();
                    break;

                case 3:
                    traitementCommande.passerCommande(gestionStocks);
                    traitementCommande.traiterCommandes(gestionStocks);
                    break;
                case 4:
                    // Enregistrement d'une vente
                    System.out.print("Entrez le nom du produit vendu : ");
                    String nomProduitVendu = scanner.nextLine();
                    Produit produitVendu = gestionStocks.rechercherProduitParNom(nomProduitVendu);

                    if (produitVendu != null) {
                        System.out.print("Entrez la quantité vendue : ");
                        int quantiteVendue = scanner.nextInt();

                        System.out.print("Entrez le prix total de la vente : ");
                        double prixTotalVente = scanner.nextDouble();

                        gestionVentes.enregistrerVente(produitVendu, quantiteVendue, prixTotalVente);
                    } else {
                        System.out.println("Produit non trouvé. Veuillez vérifier le nom du produit.");
                    }
                    break;
                case 5:
                    // Appeler les méthodes de gestion des rapports
                    gestionRapports.genererRapportVentes();
                    gestionRapports.genererRapportProduitsPlusVendus(5);
                    gestionRapports.genererRapportProduitsEnRuptureStock(gestionStocks);
                    break;
                case 0:
                    System.out.println("Programme terminé.");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        } while (choix != 0);
    }

    private static void gestionProduitsMenu(Scanner scanner, GestionProduits gestionProduits ,GestionStocks gestionStocks) {
        int choixProduits;

        do {
            System.out.println("\n----------------------------------  GESTION DES PRODUITS --------------------------------");
            System.out.println("1. Ajouter un produit");
            System.out.println("2. Supprimer un produit");
            System.out.println("3. Modifier la quantité d'un produit");
            System.out.println("4. Rechercher un produit");
            System.out.println("5. Afficher la liste des produits");
            System.out.println("0. Retourner au menu principal");
            System.out.println("------------------------------------------------------------------------------------------\n");
            System.out.print("Entrez votre choix : ");

            choixProduits = scanner.nextInt();
            scanner.nextLine();  // Pour consommer la nouvelle ligne après le choix entier

            switch (choixProduits) {
            case 1:
                // Ajout d'un produit
                gestionProduits.ajouterProduit();
                
                // Récupérer le dernier produit ajouté
                Produit produitAjoute = gestionProduits.getProduits().get(gestionProduits.getProduits().size() - 1);

                // Ajout du produit au stock
                gestionStocks.ajouterProduitAuStock(produitAjoute);
                break;

                case 2:
                	 // Vérifier si la liste des produits n'est pas vide avant de tenter de supprimer
                    if (!gestionProduits.getProduits().isEmpty()) {
                        gestionProduits.supprimerProduit();
                    } else {
                        System.out.println("La liste des produits est vide. Aucun produit à supprimer.");
                    }
                    break;
                case 3:
                    gestionProduits.modifierQuantiteProduit();
                    break;
                case 4:
                    gestionProduits.rechercherProduit();
                    break;
                case 5:
                    gestionProduits.afficherProduits();
                    break;
                case 0:
                    System.out.println("Retour au menu principal.");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        } while (choixProduits != 0); // Retourne au menu principal si choixProduits est 0
    }

}
