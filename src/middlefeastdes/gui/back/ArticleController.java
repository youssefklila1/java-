package middlefeastdes.gui.back;

import middlefeastdes.gui.back.AddCourseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ArticleController {
    @FXML
    private Button addCourseBtn;

    @FXML
    private AnchorPane productDisplayArea;

    @FXML
    private Button viewCoursesBtn;

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
