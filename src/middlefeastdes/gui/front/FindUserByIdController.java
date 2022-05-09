package middlefeastdes.gui.front;

import middlefeastdes.gui.back.*;
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
