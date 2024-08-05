import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    private List<Contact> contacts = new ArrayList<>();
    private int nextId = 1;

    public void addContact(Contact contact) {
        contact.setId(nextId++);
        contacts.add(contact);
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public void updateContact(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contact.getId()) {
                contacts.set(i, contact);
                return;
            }
        }
    }

    public void deleteContact(int id) {
        contacts.removeIf(contact -> contact.getId() == id);
    }
}
