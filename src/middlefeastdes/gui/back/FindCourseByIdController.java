package middlefeastdes.gui.back;

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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class FindCourseByIdController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblDateDebut;

    @FXML
    private Label lblDateFin;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblMode;

    private Course course;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            lblDescription.setText(course.getDescription());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDateDebut.setText(simpleDateFormat.format(course.getDateDebut()));
            lblDateFin.setText(simpleDateFormat.format(course.getDateFin()));
            lblPrice.setText(String.valueOf(course.getPrice())+ " TND");
            lblDuration.setText(Integer.parseInt(course.getDuree()) < 2 ? course.getDuree() + " Day" : course.getDuree() + " Days");
            lblMode.setText(course.getMode());
        });
    }

    @FXML
    void deleteCourse(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmDeleteCourse.fxml"));
            root = (Parent)fxmlLoader.load();
            ConfirmDeleteCourseController confirmDeleteCourseController = fxmlLoader.<ConfirmDeleteCourseController>getController();
            confirmDeleteCourseController.setCourse(course);
            Stage stage = new Stage();
            stage.setTitle("Confirm delete");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            closeWindow(btnDelete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateCourse(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCourse.fxml"));
        root = (Parent) fxmlLoader.load();
        AddCourseController addCourseController = fxmlLoader.<AddCourseController>getController();
        addCourseController.setCourse(course);
        Stage stage = new Stage();
        stage.setTitle("Update");
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
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
