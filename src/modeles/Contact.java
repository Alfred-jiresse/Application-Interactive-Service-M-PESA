package modeles;

/**
 * Représente un contact/bénéficiaire.
 */
public class Contact {
    private final int id;
    private String nom;
    private String telephone;

    public Contact(int id, String nom, String telephone) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom != null && !nom.isBlank()) {
            this.nom = nom;
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        if (telephone != null && !telephone.isBlank()) {
            this.telephone = telephone;
        }
    }

    @Override
    public String toString() {
        return "Contact{" + id + ", " + nom + ", " + telephone + "}";
    }
}
