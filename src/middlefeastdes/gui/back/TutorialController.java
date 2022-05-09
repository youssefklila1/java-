package middlefeastdes.gui.back;

import middlefeastdes.gui.back.AddCourseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.entity.Tutorial;
import middlefeastdes.service.TutorialService;

public class TutorialController {
    @FXML
    private Button addCourseBtn;

    @FXML
    private AnchorPane productDisplayArea;

    @FXML
    private Button viewCoursesBtn;
    
      @FXML
    private Button statsBtn;
      
      List<Tutorial> tutorials = new ArrayList<>();
      
      TutorialService tutorialService = new TutorialService();
      
          @FXML
    void loadStats(ActionEvent event) throws SQLException {
        
        tutorials = tutorialService.findAll();
        
          int total =0;
        
              for (Tutorial tutorial : tutorials) {
                  total+=tutorial.getPrix();
              }
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number Of Tutorials");
        xAxis.setLabel("Tutorials");       
        yAxis.setLabel("Number");
        bc.setAnimated(true);
        
        
         XYChart.Series series1 = new XYChart.Series();
        series1.setName("Tutorials");       
        series1.getData().add(new XYChart.Data("Tutorials "+tutorials.size(), tutorials.size()));
        
          XYChart.Series series2 = new XYChart.Series();
        series2.setName("Potential Income");       
        series2.getData().add(new XYChart.Data("Potential Income "+total, total));
        
        
       bc.getData().addAll(series1,series2);
       
       Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
     
        final ScrollPane scroll = new ScrollPane();
         scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        Scene scene = new Scene(scroll);
        FlowPane flowPane = new FlowPane(bc);
        scroll.setContent(flowPane);
        
        stage.setWidth(800);
        stage.setHeight(600);
           
        stage.setScene(scene);
        stage.show();

    }
    
    
    @FXML
    void loadAddCourse(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTutorial.fxml"));
        root = (Parent) fxmlLoader.load();
        AddTutorialController addTutorialController = fxmlLoader.<AddTutorialController>getController();
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(root);
    }

    @FXML
    void loadViewCourses(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("ShowAllTutorials.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);
    }
}
