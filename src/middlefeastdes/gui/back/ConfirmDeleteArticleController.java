package middlefeastdes.gui.back;

import middlefeastdes.entity.Article;
import middlefeastdes.entity.Tutorial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import middlefeastdes.service.ArticleService;
import middlefeastdes.service.TutorialService;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmDeleteArticleController {

    private Article article;
    private ArticleService articleService = new ArticleService();


    @FXML
    private Button btnReturn;

    @FXML
    private Button btnDelete;

    @FXML
    void returnToPrevious(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindArticleById.fxml"));
            root = (Parent)fxmlLoader.load();
            FindArticleByIdController findArticleByIdController = fxmlLoader.<FindArticleByIdController>getController();
            findArticleByIdController.setArticle(article);
            Stage stage = new Stage();
            stage.setTitle("Articles infos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow(btnReturn);
    }

    @FXML
    void deleteCourse(ActionEvent event) throws SQLException {
        articleService.delete(article.getId());
        closeWindow(btnDelete);
    }

    void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
