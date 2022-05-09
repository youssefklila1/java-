package middlefeastdes.gui.front;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.entity.User;
import middlefeastdes.gui.back.InfoScreenController;
import middlefeastdes.service.UserService;
import middlefeastdes.utils.Mail;

public class RegisterController {

    @FXML
    private TextField email;

    @FXML
    private TextField fname;

    @FXML
    private TextField lname;

    @FXML
    private TextField pass;

    @FXML
    private Button register_btn;

        UserService userService = new UserService();
            Alert a = new Alert(Alert.AlertType.NONE);


    @FXML
    void register(ActionEvent event) throws SQLException {
        
          if (email.getText().isEmpty() || pass.getText().isEmpty() || fname.getText().isEmpty() || lname.getText().isEmpty()) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            } else {
                      User newUser = new User(email.getText(), pass.getText(), fname.getText(), lname.getText());
                userService.add(newUser);
                Mail mailer = new Mail();
                mailer.sendEmail(newUser.getEmail(), "Account Successfuly Created !");
                openInfoScreen("Added new User successfully");
            }

    }
    
    void openInfoScreen(String message) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InfoScreen.fxml"));
            root = (Parent) fxmlLoader.load();
            InfoScreenController infoScreenController = fxmlLoader.<InfoScreenController>getController();
            infoScreenController.setMessage(message);
            Stage stage = new Stage();
            stage.setTitle("Confirmation");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
