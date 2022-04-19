/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.gui.back;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.entity.User;
import middlefeastdes.service.UserService;
import middlefeastdes.utils.Mail;

public class AddUserController implements Initializable {

    @FXML
    private Button buttonAdd;

    @FXML
    private TextArea taEmail;

    @FXML
    private TextField tfFirstname;

    @FXML
    private TextField tfLastname;

    @FXML
    private TextField tfPassword;

    private User user;

    Alert a = new Alert(Alert.AlertType.NONE);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(() -> {
            if (user != null) {
                buttonAdd.setText("Update");
                taEmail.setText(user.getEmail());
                tfFirstname.setText(user.getFirstname());
                tfLastname.setText(user.getLastname());
                //tfPassword.setDisable(true);
            }
        });

    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        User newUser = new User(taEmail.getText(), tfPassword.getText(), tfFirstname.getText(), tfLastname.getText());
        if (user == null) {
            if (taEmail.getText().isEmpty() || tfPassword.getText().isEmpty() || tfFirstname.getText().isEmpty() || tfLastname.getText().isEmpty()) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            } else {
                userService.add(newUser);
                Mail mailer = new Mail();
                mailer.sendEmail(newUser.getEmail(), "Account Successfuly Created !");
                openInfoScreen("Added new User successfully");
            }

        } else {
            if (taEmail.getText().isEmpty() || tfPassword.getText().isEmpty() || tfFirstname.getText().isEmpty() || tfLastname.getText().isEmpty()) {
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            } else {
                newUser.setId(user.getId());
                newUser.setPassword(user.getPassword());
                userService.update(newUser);
                Mail mailer = new Mail();
                mailer.sendEmail(newUser.getEmail(), "Account Successfuly Updated !");

                openInfoScreen("User updated successfully");
            }

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
