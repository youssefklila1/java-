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

public class ConfirmDeleteController{

    private Course course;
    private CourseService courseService = new CourseService();
   
    

    @FXML
    private Button btnReturn;

    @FXML
    private Button btnDelete;

    @FXML
    void returnToPrevious(ActionEvent event) {
        
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindById.fxml"));
            root = (Parent)fxmlLoader.load();
            FindCourseByIdController findByIdController = fxmlLoader.<FindCourseByIdController>getController();
            findByIdController.setCourse(course);
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
        courseService.delete(course.getId());
        closeWindow(btnDelete);
    }

    void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
