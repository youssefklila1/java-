package middlefeastdes.gui.back;

import middlefeastdes.entity.Article;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class FindArticleByIdController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView imgDisplay;

    @FXML
    private Label lblViews;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblRecipe;

    @FXML
    private Label lblTitle;

    private Article article;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Image image = new Image("http://127.0.0.1/uploads/"+article.getPicture());
            imgDisplay.setImage(image);
            lblDescription.setText(article.getDescription());
            lblTitle.setText(article.getName());
            lblRecipe.setText(String.valueOf(article.getRecette()));
            lblViews.setText(String.valueOf(article.getVues()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDate.setText(simpleDateFormat.format(article.getDate()));
        });
    }

    @FXML
    void deleteArticle(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmDeleteArticle.fxml"));
            root = (Parent)fxmlLoader.load();
            ConfirmDeleteArticleController confirmDeleteArticleController = fxmlLoader.<ConfirmDeleteArticleController>getController();
            confirmDeleteArticleController.setArticle(article);
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
    void updateArticle(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddArticle.fxml"));
        root = (Parent) fxmlLoader.load();
        AddArticleController addArticleController = fxmlLoader.<AddArticleController>getController();
        addArticleController.setArticle(article);
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

    public void setArticle(Article article) {
        this.article = article;
    }
}
