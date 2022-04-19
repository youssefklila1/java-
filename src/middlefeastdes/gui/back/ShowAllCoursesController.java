package middlefeastdes.gui.back;

import middlefeastdes.entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import middlefeastdes.service.CourseService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllCoursesController implements Initializable {

    @FXML
    private ScrollPane scrollTable;

    CourseService courseService = new CourseService();
    List<Course> courses = new ArrayList<>();

    private final ObservableList<Course> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data.clear();
        try {
            GridPane grid = new GridPane();
            grid.add(new Label("Description"), 0, 0, 1, 1);
            grid.add(new Label("Mode"), 1, 0, 1, 1);
            grid.add(new Label("Starting date"), 2, 0, 1, 1);
            grid.add(new Label("Ending date"), 3, 0, 1, 1);
            grid.add(new Label("Duration"), 4, 0, 1, 1);
            grid.add(new Label("Price"), 5, 0, 1, 1);
            grid.add(new Label("Details"), 6, 0, 1, 1);
            grid.setHgap(60);
            grid.setVgap(30);
            grid.setPadding(new Insets(10));
            grid.setStyle(
                    "-fx-background-color: #121212;"
            );
            courses = courseService.findAll();
            if(courses.isEmpty()){
                Pane emptyPane = new Pane();
                emptyPane.setPrefWidth(920);
                emptyPane.setPrefHeight(486);
                emptyPane.setStyle("-fx-background-color: #121212;");
                Label emptyLabel = new Label("No items found");
                emptyLabel.setStyle("" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-size: 17;" +
                        "-fx-font-weight: bold");
                emptyLabel.layoutXProperty().bind(emptyPane.widthProperty().subtract(emptyLabel.widthProperty()).divide(2));
                emptyLabel.layoutYProperty().bind(emptyPane.heightProperty().subtract(emptyLabel.heightProperty()).divide(2));
                emptyPane.getChildren().add(emptyLabel);
                scrollTable.setContent(emptyPane);

            }else {
                data.addAll(courses);
                for (int i = 0; i < data.size(); i++) {
                    Label lblDescription = new Label(data.get(i).getDescription());
                    lblDescription.setTextFill(Color.WHITE);
                    Label lblMode = new Label(data.get(i).getMode());
                    lblMode.setTextFill(Color.WHITE);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Label lblDateDebut = new Label(simpleDateFormat.format(data.get(i).getDateDebut()));
                    lblDateDebut.setTextFill(Color.WHITE);
                    Label lblDateFin = new Label(simpleDateFormat.format(data.get(i).getDateFin()));
                    lblDateFin.setTextFill(Color.WHITE);
                    Label lblDuree = new Label(data.get(i).getDuree());
                    lblDuree.setTextFill(Color.WHITE);
                    Label lblPrice = new Label(String.valueOf(data.get(i).getPrice()) + "TND");
                    lblPrice.setTextFill(Color.WHITE);
                    grid.add(lblDescription, 0, i + 1, 1, 1);
                    grid.add(lblMode, 1, i + 1, 1, 1);
                    grid.add(lblDateDebut, 2, i + 1, 1, 1);
                    grid.add(lblDateFin, 3, i + 1, 1, 1);
                    grid.add(lblDuree, 4, i + 1, 1, 1);
                    grid.add(lblPrice, 5, i + 1, 1, 1);
                    grid.add(DetailsButton(data.get(i), resources), 6, i + 1, 1, 1);

                    scrollTable.setContent(grid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Button DetailsButton(Course course, ResourceBundle resources) {
        Button btn = new Button("Details");
        btn.setTextFill(Color.WHITE);
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: #2a73ff;" +
                        "-fx-border-radius: 20;");
        btn.setOnMouseEntered(event -> {
            btn.setStyle(
                    "-fx-background-color: #2a73ff;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: transparent;" +
                            "-fx-border-radius: 20;" +
                            "-fx-animated: 1000;"
            );
            btn.setTextFill(Color.color(0.1647, 0.451, 1.0));
        });
        btn.setOnMouseExited(event -> {
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: #2a73ff;" +
                            "-fx-border-radius: 20;" +
                            "-fx-animated: 1000;"
            );
            btn.setTextFill(Color.WHITE);
        });
        btn.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindCourseById.fxml"));
                root = (Parent)fxmlLoader.load();
                FindCourseByIdController findCourseByIdController = fxmlLoader.<FindCourseByIdController>getController();
                findCourseByIdController.setCourse(course);
                Stage stage = new Stage();
                stage.setTitle("Courses infos");
                stage.resizableProperty().setValue(false);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return btn;
    }
}
