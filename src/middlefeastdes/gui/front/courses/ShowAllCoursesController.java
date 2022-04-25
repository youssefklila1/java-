package middlefeastdes.gui.front.courses;

import middlefeastdes.entity.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import middlefeastdes.service.CourseService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
        try{
            GridPane grid = new GridPane();
            grid.setVgap(50);
            grid.setHgap(100);
            courses = courseService.findAll();
            if(courses.isEmpty()){
                Pane emptyPane = new Pane();
                emptyPane.setPrefWidth(950);
                emptyPane.setPrefHeight(486);
                emptyPane.setStyle("-fx-background-color: #ffffff;");
                Label emptyLabel = new Label("No items found");
                emptyLabel.setStyle("" +
                        "-fx-text-fill: #000000;" +
                        "-fx-font-size: 17;" +
                        "-fx-font-weight: bold");
                emptyLabel.layoutXProperty().bind(emptyPane.widthProperty().subtract(emptyLabel.widthProperty()).divide(2));
                emptyLabel.layoutYProperty().bind(emptyPane.heightProperty().subtract(emptyLabel.heightProperty()).divide(2));
                emptyPane.getChildren().add(emptyLabel);
                scrollTable.setContent(emptyPane);
            }else{
                data.addAll(courses);
                int row = -1;
                for (int i = 0; i < data.size(); i++) {
                    int col = i%2 == 0 ? 0 : 1;

                    Label lblDescription = new Label(data.get(i).getDescription());
                    lblDescription.setStyle("" +
                            "-fx-font-size: 17;");
                    Label lblPrice = new Label(data.get(i).getPrice() > 0 ? String.valueOf(data.get(i).getPrice()) + " TND" : "Free");
                    lblPrice.setStyle("" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 17;");
                    Label lblMode = new Label(data.get(i).getMode());
                    lblPrice.setStyle("" +
                            "-fx-font-weight: bold;" +
                            "-fx-font-size: 17;");
                    VBox vBox = new VBox();
                    Course tmp = data.get(i);
                    vBox.setOnMouseClicked(event -> openDetails(tmp));
                    vBox.setMaxWidth(300);
                    vBox.setMaxHeight(400);

                    vBox.getChildren().addAll(lblDescription, lblMode, lblPrice);

                    if(i%2==0){
                        row++;
                    }

                    grid.add(vBox, col, row);

                }

                scrollTable.setContent(grid);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void openDetails(Course course){
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../gui/front/courses/FindCourseById.fxml"));
            root = (Parent)fxmlLoader.load();
            FindCourseByIdController findCourseByIdController = fxmlLoader.<FindCourseByIdController>getController();
            findCourseByIdController.setCourse(course);
            Stage stage = new Stage();
            stage.setTitle("Course infos");
            stage.resizableProperty().setValue(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
