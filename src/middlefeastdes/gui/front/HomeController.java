package middlefeastdes.gui.front;

import middlefeastdes.gui.back.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

public class HomeController implements Initializable {

    @FXML
    private Button btnArticles;

    @FXML
    private Button btnCourses;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnTutorials;

    @FXML
    private Button btnWishlist;
    
     @FXML
    private Button btnProfile;

    @FXML
    private AnchorPane displyaArea;
    
            Alert a = new Alert(Alert.AlertType.NONE);

    
       @Override
    public void initialize(URL location, ResourceBundle resources) {
         UserSession userSession = new UserSession();
         Platform.runLater(() -> {
             if(userSession.getConnectedUser()!=null){
                   btnProfile.setVisible(true);
            btnWishlist.setVisible(true);
            btnSignin.setText("Signout");
            btnSignin.setOnAction((event) -> {
                signout(event);
            });
             }
             
        });
        if(userSession.getConnectedUser()==null)
        {
            btnProfile.setOnAction((event) -> {
                 a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Please Login !");
                a.show();
            });
            btnWishlist.setOnAction((event) -> {
                 a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Please Login !");
                a.show();
            });
        }
    }
    
        @FXML
    void Signin(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../../gui/front/Login.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);
    }
    
    
    @FXML
    void wishlist(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../../gui/front/wishlists/Wishlists.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);
    }


    @FXML
    void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void loadArticles(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../../gui/front/articles/Articles.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);
    }

    @FXML
    void loadCourses(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../../gui/front/courses/Courses.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);
    }

    @FXML
    void loadTutorials(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("../../gui/front/tutorials/Tutorials.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);
    }
    
      @FXML
    void loadProfile(ActionEvent event) {

    }

    @FXML
    void loadUsers(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("Users.fxml"));
        displyaArea.getChildren().clear();
        displyaArea.getChildren().add(fxml);

    }

    @FXML
    void minimize_app(MouseEvent event) {

    }

    @FXML
    void signout(ActionEvent event) {

    }

 

}
