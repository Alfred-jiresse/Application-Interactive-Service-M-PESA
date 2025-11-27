package depot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modeles.Contact;

public class DepotContacts {
    private final List<Contact> contacts = new ArrayList<>();

    public List<Contact> trouverTous() {
        return new ArrayList<>(contacts);
    }

    public Optional<Contact> trouverParId(int id) {
        return contacts.stream().filter(c -> c.getId() == id).findFirst();
    }

    public void sauvegarder(Contact contact) {
        contacts.add(contact);
    }

    public boolean supprimerParId(int id) {
        return contacts.removeIf(c -> c.getId() == id);
    }
}
