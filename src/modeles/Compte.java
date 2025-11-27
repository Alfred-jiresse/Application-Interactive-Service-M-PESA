package modeles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import enumerations.Devise;
import modeles.Transaction;

/**
 * Classe abstraite représentant un compte.
 * Encapsulation, abstraction et méthodes polymorphes.
 */
public abstract class Compte {
    private final int id;
    private String nomTitulaire;
    private double solde;
    private final Devise devise;
    private final List<Transaction> transactions = new ArrayList<>();

    protected Compte(int id, String nomTitulaire, Devise devise) {
        this.id = id;
        this.nomTitulaire = Objects.requireNonNull(nomTitulaire);
        this.devise = Objects.requireNonNull(devise);
        this.solde = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getNomTitulaire() {
        return nomTitulaire;
    }

    public void setNomTitulaire(String nomTitulaire) {
        if (nomTitulaire != null && !nomTitulaire.isBlank()) {
            this.nomTitulaire = nomTitulaire;
        }
    }

    public double getSolde() {
        return solde;
    }

    protected void setSolde(double solde) {
        this.solde = solde;
    }

    public Devise getDevise() {
        return devise;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void ajouterTransaction(Transaction t) {
        transactions.add(t);
    }

    public abstract boolean retirer(double montant);

    public abstract void deposer(double montant);

    @Override
    public String toString() {
        return "Compte{" +
                "id=" + id +
                ", titulaire='" + nomTitulaire + '\'' +
                ", solde=" + solde +
                ", devise=" + devise +
                '}';
    }
}
