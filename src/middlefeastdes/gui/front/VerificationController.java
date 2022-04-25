package middlefeastdes.gui.front;

import java.io.IOException;
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
import middlefeastdes.service.UserService;

public class VerificationController {
    
    int code;
    String email;

    @FXML
    private TextField txt_code;

    @FXML
    private Button verifybtn;
    
            Alert a = new Alert(Alert.AlertType.NONE);

    UserService userService = new UserService();
    @FXML
    void verify(ActionEvent event) {
        
        if(Integer.parseInt(txt_code.getText())==code)
        {
            Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resetPassword.fxml"));
            root = (Parent)fxmlLoader.load();

            
             ResetPasswordController resetPasswordController = fxmlLoader.<ResetPasswordController>getController();
            resetPasswordController.setEmail(email);
            
            
            Stage stage = new Stage();
            stage.setTitle("Password Reset");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        }else
        {
             a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Code Does Not Match , Please Verifiy Your Email !");
                a.show();
        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    
}
