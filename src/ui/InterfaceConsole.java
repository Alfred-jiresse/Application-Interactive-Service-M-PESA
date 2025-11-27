package ui;

import java.util.List;
import java.util.Scanner;
import modeles.Compte;
import modeles.Transaction;
import modeles.Contact;
import enumerations.Devise;
import services.ServiceComptes;

public class InterfaceConsole {
    private final Scanner scanner;
    private final ServiceComptes service;

    public InterfaceConsole(Scanner scanner) {
        this.scanner = scanner;
        this.service = new ServiceComptes();
        initialiserDonneesDemo();
    }

    private void initialiserDonneesDemo() {
        service.creerCompte("Alice", Devise.USD);
        service.creerCompte("Bob", Devise.CDF);
        service.deposer(1, 50.0);
        service.deposer(2, 10000.0);
    }

    public void demarrer() {
        boolean enCours = true;
        while (enCours) {
            afficherMenuPrincipal();
            int choix = lireEntier();
            switch (choix) {
                case 1 -> creerCompte();
                case 2 -> listerComptes();
                case 3 -> modifierCompte();
                case 4 -> supprimerCompte();
                case 5 -> menuOperationsCompte();
                case 6 -> gererContacts();
                case 0 -> {
                    System.out.println("Au revoir !");
                    enCours = false;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void afficherMenuPrincipal() {
        System.out.println("\nAPPLICATION M-WALLET (démo)");
        System.out.println("1. Créer un compte");
        System.out.println("2. Lister les comptes");
        System.out.println("3. Modifier un compte");
        System.out.println("4. Supprimer un compte");
        System.out.println("5. Opérations sur compte (envoi/dépôt/retrait/historique)");
        System.out.println("6. Gérer contacts");
        System.out.println("0. Quitter");
        System.out.print("Choix : ");
    }

    private void creerCompte() {
        System.out.print("Nom du titulaire : ");
        String nom = scanner.nextLine().trim();
        System.out.println("Choisir la devise : 1. USD  2. CDF");
        System.out.print("Choix : ");
        int c = lireEntier();
        Devise devise = (c == 1) ? Devise.USD : Devise.CDF;
        Compte compte = service.creerCompte(nom, devise);
        System.out.println("Compte créé : " + compte);
    }

    private void listerComptes() {
        List<Compte> comptes = service.tousLesComptes();
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte enregistré.");
            return;
        }
        System.out.println("Liste des comptes :");
        comptes.forEach(System.out::println);
    }

    private void modifierCompte() {
        System.out.print("ID du compte à modifier : ");
        int id = lireEntier();
        Compte compte = service.trouverParId(id);
        if (compte == null) {
            System.out.println("Compte introuvable.");
            return;
        }
        System.out.println("Nom actuel : " + compte.getNomTitulaire());
        System.out.print("Nouveau nom (laisser vide pour ne pas changer) : ");
        String nouveauNom = scanner.nextLine().trim();
        if (!nouveauNom.isEmpty()) {
            service.modifierNom(id, nouveauNom);
        }
        System.out.println("Compte mis à jour : " + service.trouverParId(id));
    }

    private void supprimerCompte() {
        System.out.print("ID du compte à supprimer : ");
        int id = lireEntier();
        boolean ok = service.supprimerCompte(id);
        System.out.println(ok ? "Compte supprimé." : "Compte introuvable.");
    }

    private void menuOperationsCompte() {
        System.out.print("ID du compte : ");
        int id = lireEntier();
        Compte compte = service.trouverParId(id);
        if (compte == null) {
            System.out.println("Compte introuvable.");
            return;
        }

        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Opérations (Compte " + compte.getId() + " - " + compte.getNomTitulaire() + ") ---");
            System.out.println("1. Déposer");
            System.out.println("2. Retirer");
            System.out.println("3. Envoyer à un autre compte");
            System.out.println("4. Voir solde");
            System.out.println("5. Voir historique");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            int c = lireEntier();
            switch (c) {
                case 1 -> {
                    System.out.print("Montant à déposer : ");
                    double montant = lireDouble();
                    service.deposer(id, montant);
                    System.out.println("Dépôt effectué. Nouveau solde : " + compte.getSolde());
                }
                case 2 -> {
                    System.out.print("Montant à retirer : ");
                    double montant = lireDouble();
                    boolean ok = service.retirer(id, montant);
                    System.out.println(ok ? "Retrait réussi. Nouveau solde : " + compte.getSolde()
                            : "Retrait échoué (solde insuffisant).");
                }
                case 3 -> {
                    System.out.print("ID compte destinataire : ");
                    int dest = lireEntier();
                    System.out.print("Montant à envoyer : ");
                    double montant = lireDouble();
                    boolean ok = service.transferer(id, dest, montant);
                    System.out.println(ok ? "Envoi réussi." : "Envoi échoué (vérifier solde / destinataire / devise).");
                }
                case 4 -> System.out.println("Solde : " + compte.getSolde() + " " + compte.getDevise());
                case 5 -> {
                    List<Transaction> tx = service.historiqueTransactions(id);
                    if (tx.isEmpty()) {
                        System.out.println("Aucune transaction.");
                    } else {
                        System.out.println("Historique :");
                        tx.forEach(System.out::println);
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void gererContacts() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion Contacts ---");
            System.out.println("1. Créer contact");
            System.out.println("2. Lister contacts");
            System.out.println("3. Supprimer contact");
            System.out.println("0. Retour");
            System.out.print("Choix : ");
            int c = lireEntier();
            switch (c) {
                case 1 -> {
                    System.out.print("Nom du contact : ");
                    String nom = scanner.nextLine().trim();
                    System.out.print("Numéro (texte) : ");
                    String tel = scanner.nextLine().trim();
                    service.creerContact(nom, tel);
                    System.out.println("Contact créé.");
                }
                case 2 -> {
                    List<Contact> contacts = service.tousLesContacts();
                    if (contacts.isEmpty()) {
                        System.out.println("Aucun contact.");
                    } else {
                        contacts.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("ID du contact : ");
                    int id = lireEntier();
                    boolean ok = service.supprimerContact(id);
                    System.out.println(ok ? "Contact supprimé." : "Contact introuvable.");
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private int lireEntier() {
        while (true) {
            String ligne = scanner.nextLine().trim();
            try {
                return Integer.parseInt(ligne);
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide, recommencez : ");
            }
        }
    }

    private double lireDouble() {
        while (true) {
            String ligne = scanner.nextLine().trim();
            try {
                return Double.parseDouble(ligne);
            } catch (NumberFormatException e) {
                System.out.print("Entrée invalide, recommencez : ");
            }
        }
    }
}
