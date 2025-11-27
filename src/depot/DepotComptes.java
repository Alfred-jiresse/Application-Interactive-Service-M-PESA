package depot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modeles.Compte;

/**
 * Dépôt en mémoire pour les comptes (List).
 */
public class DepotComptes {
    private final List<Compte> comptes = new ArrayList<>();

    public List<Compte> trouverTous() {
        return new ArrayList<>(comptes);
    }

    public Optional<Compte> trouverParId(int id) {
        return comptes.stream().filter(c -> c.getId() == id).findFirst();
    }

    public void sauvegarder(Compte compte) {
        comptes.add(compte);
    }

    public boolean supprimerParId(int id) {
        return comptes.removeIf(c -> c.getId() == id);
    }
}
