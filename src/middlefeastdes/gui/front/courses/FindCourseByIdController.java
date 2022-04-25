package middlefeastdes.gui.front.courses;

import middlefeastdes.entity.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class FindCourseByIdController implements Initializable {


    @FXML
    private Button btnCart;

    @FXML
    private Label lblDateDebut;

    @FXML
    private Label lblDateFin;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblMode;

    @FXML
    private Label lblPrice;

    private Course course;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            lblDescription.setText(course.getDescription());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDateDebut.setText(simpleDateFormat.format(course.getDateDebut()));
            lblDateFin.setText(simpleDateFormat.format(course.getDateFin()));
            lblPrice.setText(course.getPrice() > 0 ? String.valueOf(course.getPrice())+ " TND" : "Free");
            lblDuration.setText(Integer.parseInt(course.getDuree()) < 2 ? course.getDuree() + " Day" : course.getDuree() + " Days");
            lblMode.setText(course.getMode());
            if(!(course.getPrice() > 0)){
                btnCart.setText("Get now!");
            }
        });
    }

    @FXML
    void addToCart(ActionEvent event) {

    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
