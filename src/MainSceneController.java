import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class MainSceneController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private ListView<Contact> contactListView;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    private ContactDAO contactDAO = new ContactDAO();
    private ObservableList<Contact> contactObservableList;

    @FXML
    public void initialize() {
        contactObservableList = FXCollections.observableArrayList();
        contactListView.setItems(contactObservableList);
        refreshContactList();
    }

    @FXML
    void addContact(ActionEvent event) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!isValidInput(name, phone, email)) {
            return;
        }

        Contact contact = new Contact(0, name, phone, email);
        contactDAO.addContact(contact);
        refreshContactList();
        clearForm();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Contact added successfully!");
    }

    @FXML
    void editContact(ActionEvent event) {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No contact selected!");
            return;
        }

        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (!isValidInput(name, phone, email)) {
            return;
        }

        selectedContact.setName(name);
        selectedContact.setPhone(phone);
        selectedContact.setEmail(email);
        contactDAO.updateContact(selectedContact);
        refreshContactList();
        clearForm();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Contact updated successfully!");
    }

    @FXML
    void deleteContact(ActionEvent event) {
        Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
        if (selectedContact == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No contact selected!");
            return;
        }

        contactDAO.deleteContact(selectedContact.getId());
        refreshContactList();
        clearForm();
        showAlert(Alert.AlertType.INFORMATION, "Success", "Contact deleted successfully!");
    }

    private boolean isValidInput(String name, String phone, String email) {
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled!");
            return false;
        }

        if (!isAlphabetic(name)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name must contain only alphabetic characters!");
            return false;
        }

        if (!isNumeric(phone)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Phone number must be a numeric value!");
            return false;
        }

        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email must be in a valid format (e.g., someone@gmail.com)!");
            return false;
        }

        return true;
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isAlphabetic(String str) {
        return str.matches("[a-zA-Z]+");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }

    private void refreshContactList() {
        contactObservableList.setAll(contactDAO.getAllContacts());
    }

    private void clearForm() {
        nameField.clear();
        phoneField.clear();
        emailField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
