package middlefeastdes.gui.front;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private Button btnBack;

    @FXML
    private AnchorPane displyaArea;

    Alert a = new Alert(Alert.AlertType.NONE);

    UserSession userSession = UserSession.getInstace();

    @FXML
    void Backend(ActionEvent event) throws IOException {
        Parent root;
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../gui/back/Home.fxml"));
            root = (Parent)fxmlLoader.load();
            middlefeastdes.gui.back.HomeController homeController = fxmlLoader.<middlefeastdes.gui.back.HomeController>getController();
            Stage stage = new Stage();
            stage.setTitle("Backend");
            stage.setScene(new Scene(root));
            stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
  btnBack.setVisible(false);
        Platform.runLater(() -> {
            if (userSession.getConnectedUser() != null) {
                
                if( userSession.getConnectedUser().getEmail().toLowerCase().contains("admin") || userSession.getConnectedUser().getEmail().toLowerCase().contains("youssef") || userSession.getConnectedUser().getEmail().toLowerCase().contains("mariem") || userSession.getConnectedUser().getEmail().toLowerCase().contains("fatma"))
                {
                    btnBack.setVisible(true);
                }
                btnSignin.setText("Signout");
                btnSignin.setOnAction((event) -> {
                    try {
                        signout(event);
                    } catch (IOException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });

        if (userSession.getConnectedUser() == null) {
            
            btnBack.setVisible(false);
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
    void loadProfile(ActionEvent event) throws IOException {
        
            Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindUserById.fxml"));
        root = (Parent) fxmlLoader.load();
        FindUserByIdController FindUserController = fxmlLoader.<FindUserByIdController>getController();
        FindUserController.setUser(userSession.getConnectedUser());
        Stage stage = new Stage();
        stage.setTitle("Profile");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

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
    void signout(ActionEvent event) throws IOException {
        
        userSession.setConnectedUser(null);

         Stage stg = (Stage) btnSignin.getScene().getWindow();
               Stage main = (Stage) stg.getScene().getWindow();

            Parent root;
                
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Home.fxml"));
            root = (Parent)fxmlLoader.load();
                   HomeController homeController = fxmlLoader.<HomeController>getController();
        main.setScene(new Scene(root));
        
        main.show();
        

    }

}
