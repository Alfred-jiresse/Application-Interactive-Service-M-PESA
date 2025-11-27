package modeles;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import enumerations.TypeTransaction;
import enumerations.Devise;

public class Transaction {
    private final TypeTransaction type;
    private final double montant;
    private final Devise devise;
    private final LocalDateTime dateHeure;
    private final String description;

    public Transaction(TypeTransaction type, double montant, Devise devise, String description) {
        this.type = type;
        this.montant = montant;
        this.devise = devise;
        this.description = description;
        this.dateHeure = LocalDateTime.now();
    }

    public TypeTransaction getType() {
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public Devise getDevise() {
        return devise;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + dateHeure.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] "
                + type + " " + montant + " " + devise + " - " + description;
    }
}
