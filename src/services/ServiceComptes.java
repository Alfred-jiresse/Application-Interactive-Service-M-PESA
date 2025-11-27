package services;

import java.util.List;
import java.util.stream.Collectors;
import depot.DepotComptes;
import depot.DepotContacts;
import enumerations.Devise;
import enumerations.TypeTransaction;
import modeles.Compte;
import modeles.CompteUSD;
import modeles.CompteCDF;
import modeles.Contact;
import modeles.Transaction;
import util.GenerateurId;

/**
 * gestion contacts et les service lier au compte comme creer le compte supprimer et d'autre operatio
 * en gros juste la gestion de compte.
 */
public class ServiceComptes {
    private final DepotComptes depotComptes = new DepotComptes();
    private final DepotContacts depotContacts = new DepotContacts();

    public Compte creerCompte(String nomTitulaire, Devise devise) {
        int id = GenerateurId.suivant();
        Compte compte;
        if (devise == Devise.USD) {
            compte = new CompteUSD(id, nomTitulaire);
        } else {
            compte = new CompteCDF(id, nomTitulaire);
        }
        depotComptes.sauvegarder(compte);
        return compte;
    }

    public List<Compte> tousLesComptes() {
        return depotComptes.trouverTous();
    }

    public Compte trouverParId(int id) {
        return depotComptes.trouverParId(id).orElse(null);
    }

    public void modifierNom(int id, String nouveauNom) {
        depotComptes.trouverParId(id).ifPresent(c -> c.setNomTitulaire(nouveauNom));
    }

    public boolean supprimerCompte(int id) {
        return depotComptes.supprimerParId(id);
    }

    public boolean deposer(int id, double montant) {
        Compte c = trouverParId(id);
        if (c == null || montant <= 0) return false;
        c.deposer(montant);
        return true;
    }

    public boolean retirer(int id, double montant) {
        Compte c = trouverParId(id);
        if (c == null || montant <= 0) return false;
        return c.retirer(montant);
    }

    public boolean transferer(int idSource, int idDest, double montant) {
        if (montant <= 0) return false;
        Compte source = trouverParId(idSource);
        Compte dest = trouverParId(idDest);
        if (source == null || dest == null) return false;
        if (!source.getDevise().equals(dest.getDevise())) {
            // pour l'Atelier je ne pourrais aller plus loin et implementer la conversion d'ou je me limite ici
            return false;
        }
        synchronized (this) {
            if (!source.retirer(montant)) return false;
            dest.deposer(montant);
            source.ajouterTransaction(new Transaction(TypeTransaction.TRANSFERT_SORTIE, montant, source.getDevise(),
                    "Envoi vers compte " + idDest));
            dest.ajouterTransaction(new Transaction(TypeTransaction.TRANSFERT_ENTREE, montant, dest.getDevise(),
                    "Réception depuis compte " + idSource));
            return true;
        }
    }

    //je continue plus tard plus d'inspiration pour la suite//

    // Contacts CRUD
    public Contact creerContact(String nom, String telephone) {
        int id = GenerateurId.suivant();
        Contact c = new Contact(id, nom, telephone);
        depotContacts.sauvegarder(c);
        return c;
    }

    public List<Contact> tousLesContacts() {
        return depotContacts.trouverTous();
    }

    public boolean supprimerContact(int id) {
        return depotContacts.supprimerParId(id);
    }

    public List<Transaction> historiqueTransactions(int idCompte) {
        Compte c = trouverParId(idCompte);
        if (c == null) return List.of();
        return c.getTransactions().stream().collect(Collectors.toList());
    }
}
//fini pour aujourd'hui on continue samedi