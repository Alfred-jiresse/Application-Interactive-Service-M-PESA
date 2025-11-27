package modeles;

import enumerations.Devise;
import enumerations.TypeTransaction;

public class CompteUSD extends Compte {

    public CompteUSD(int id, String nomTitulaire) {
        super(id, nomTitulaire, Devise.USD);
    }

    @Override
    public boolean retirer(double montant) {
        if (montant <= 0) return false;
        if (getSolde() >= montant) {
            setSolde(getSolde() - montant);
            ajouterTransaction(new Transaction(TypeTransaction.RETRAIT, montant, getDevise(), "Retrait USD"));
            return true;
        }
        return false;
    }

    @Override
    public void deposer(double montant) {
        if (montant <= 0) return;
        setSolde(getSolde() + montant);
        ajouterTransaction(new Transaction(TypeTransaction.DEPOT, montant, getDevise(), "Dépôt USD"));
    }
}
