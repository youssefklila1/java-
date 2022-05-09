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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.entity.Article;
import middlefeastdes.service.ArticleService;

public class ArticleController {

    @FXML
    private Button addCourseBtn;

    @FXML
    private AnchorPane productDisplayArea;

    @FXML
    private Button viewCoursesBtn;

    @FXML
    private Button statsBtn;

    List<Article> articles = new ArrayList<>();
    ArticleService articleService = new ArticleService();

    @FXML
    void loadStats(ActionEvent event) throws SQLException {

        articles = articleService.findAll();
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();

        articles.forEach((data) -> {
            list.add(new PieChart.Data(data.getName()+" "+data.getVues(), data.getVues()));
        });

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        list);
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Vues Per Articles");
        
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number Of Articles");
        xAxis.setLabel("Articles");       
        yAxis.setLabel("Number");
        bc.setAnimated(true);
        
        
         XYChart.Series series1 = new XYChart.Series();
        series1.setName("Articles");       
        series1.getData().add(new XYChart.Data("Articles "+list.size(), list.size()));
       bc.getData().addAll(series1);
        
        
        
        

        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
     
        final ScrollPane scroll = new ScrollPane();
         scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        Scene scene = new Scene(scroll);
        FlowPane flowPane = new FlowPane(chart,bc);
        scroll.setContent(flowPane);
        
        stage.setWidth(800);
        stage.setHeight(600);
           
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void loadAddCourse(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
        root = (Parent) fxmlLoader.load();
        AddArticleController addArticleController = fxmlLoader.<AddArticleController>getController();
        addArticleController.setArticle(null);
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(root);
    }

    @FXML
    void loadViewCourses(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("ShowAllArticles.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);
    }
}
