package middlefeastdes.gui.back;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class UsersController {

    @FXML
    private Button addUserBtn;

    @FXML
    private AnchorPane productDisplayArea;

    @FXML
    private Button viewUsersBtn;

    @FXML
    void loadAddUser(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);

    }

    @FXML
    void loadViewUsers(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("ShowAllUsers.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);

    }

}
