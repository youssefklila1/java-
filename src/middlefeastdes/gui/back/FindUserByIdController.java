package middlefeastdes.gui.back;

import middlefeastdes.entity.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import middlefeastdes.entity.User;

public class FindUserByIdController implements Initializable {
   @FXML
    private Button btnDelete;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;
    
    private User user;

    @FXML
    void deleteUser(ActionEvent event) {
         Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmDeleteUser.fxml"));
            root = (Parent)fxmlLoader.load();
            ConfirmDeleteUserController confirmDeleteController = fxmlLoader.<ConfirmDeleteUserController>getController();
            confirmDeleteController.setUser(user);
            Stage stage = new Stage();
            stage.setTitle("Confirm delete");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closeWindow(btnDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void updateUser(ActionEvent event) throws IOException {
           Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        root = (Parent) fxmlLoader.load();
        AddUserController addController = fxmlLoader.<AddUserController>getController();
        addController.setUser(user);
        Stage stage = new Stage();
        stage.setTitle("Update");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        closeWindow(btnDelete);

    }

    void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         Platform.runLater(() -> {
            lblEmail.setText(user.getEmail());
            lblFirstName.setText(user.getFirstname());
            lblLastName.setText(user.getLastname());
           
        });
    }
}
