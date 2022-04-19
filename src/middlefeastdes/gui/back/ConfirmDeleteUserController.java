package middlefeastdes.gui.back;

import middlefeastdes.entity.Course;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import middlefeastdes.service.CourseService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import middlefeastdes.entity.User;
import middlefeastdes.service.UserService;

public class ConfirmDeleteUserController{

    private User user;
    private UserService userService = new UserService();
   
    

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnDelete;

    @FXML
    void returnToPrevious(ActionEvent event) {
        
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindUserById.fxml"));
            root = (Parent)fxmlLoader.load();
            FindUserByIdController findByIdController = fxmlLoader.<FindUserByIdController>getController();
            findByIdController.setUser(user);
            Stage stage = new Stage();
            stage.setTitle("Courses infos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow(btnReturn);
    }

    @FXML
    void deleteCourse(ActionEvent event) throws SQLException {
        userService.delete(user.getId());
        closeWindow(btnDelete);
    }

    void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void setUser(User user) {
        this.user = user;
    }
}
