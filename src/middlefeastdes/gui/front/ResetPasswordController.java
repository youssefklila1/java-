package middlefeastdes.gui.front;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import middlefeastdes.service.UserService;

public class ResetPasswordController {
    
    String email;

    @FXML
    private Button resetPassBtn;

    @FXML
    private TextField txt_Newpassword;
    
    UserService userService = new UserService();
            Alert a = new Alert(Alert.AlertType.NONE);

    
    
    @FXML
    void resetPassword(ActionEvent event) {
        
        try {
            userService.updatePassword(email, txt_Newpassword.getText());
             a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Password Updated Go to Login Page !");
                a.show();
        } catch (SQLException ex) {
             a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Error While Updating Password !");
                a.show();
        }

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
