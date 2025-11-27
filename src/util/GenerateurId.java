package util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * pour generer des identifian unique pour chaque utilisateur.
 * comment ? en augmentant la valeur de suivant a chaque fois je ne pas tester dans tout les cas
 * mais dans mon cas ce bon
 */
public class GenerateurId {
    private static final AtomicInteger COMPTEUR = new AtomicInteger(0);

    public static int suivant() {
        return COMPTEUR.incrementAndGet();
    }
}
