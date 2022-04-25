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
import middlefeastdes.gui.front.articles.InfoScreenArticleController;
import middlefeastdes.service.UserService;

public class LoginController {
    
    public static User connectedUser=null;
    UserService userService = new UserService();

    @FXML
    private Button btnforgotpass;

    @FXML
    private Button loginbtn;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_pass;

    @FXML
    void forgotPass(ActionEvent event) {
        
        UserService userService = new UserService();
        
        if(txt_email.getText().isEmpty())
        {
             a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Please Provide Your Email");
                a.show();
        }else
        {
        
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Verification.fxml"));
            root = (Parent)fxmlLoader.load();

            
             VerificationController verificationController = fxmlLoader.<VerificationController>getController();
            verificationController.setCode(userService.resetPassword(txt_email.getText()));
            verificationController.setEmail(txt_email.getText());
            
            
            Stage stage = new Stage();
            stage.setTitle("Code Verification");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        
          

    }
    
        Alert a = new Alert(Alert.AlertType.NONE);


    @FXML
    void login(ActionEvent event) throws SQLException {
        UserSession userSession = new UserSession();
        userSession.setConnectedUser(userService.login(txt_email.getText(), txt_pass.getText()));
        if(userSession.getConnectedUser()!=null)
        {
             a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Login Successfull !");
                a.show();
        }else
        {
            a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Email or Password wrong!");
                a.show();
        }

    }

}
