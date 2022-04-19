package middlefeastdes.gui.back;

import middlefeastdes.entity.Article;
import middlefeastdes.entity.Tutorial;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.service.ArticleService;
import middlefeastdes.service.TutorialService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.Alert.AlertType;

public class AddArticleController implements Initializable {

    @FXML
    private Button ImageArticle;

    @FXML
    private Button buttonAdd;

    @FXML
    private Label lblTask;

    @FXML
    private TextField nomImage;

    @FXML
    private TextArea taDescription;

    @FXML
    private TextArea taReciep;

    @FXML
    private TextField tfName;

    private Article article;

    ArticleService articleService = new ArticleService();

    Alert a = new Alert(AlertType.NONE);

    @FXML
    private String selectImage(ActionEvent event) {
        final FileChooser image = new FileChooser();
        image.setTitle("Select an image ");
        String path = image.showOpenDialog(ImageArticle.getScene().getWindow()).getName();
        nomImage.setText(path);
        return "http://127.0.0.1/uploads/" + path;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            if (article != null) {
                lblTask.setText("Update Article");
                buttonAdd.setText("Update");
                tfName.setText(article.getName());
                taDescription.setText(article.getDescription());
                taReciep.setText(article.getRecette());
                nomImage.setText(article.getPicture());
            }
        });
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        Article newArticle = new Article(tfName.getText(), taDescription.getText(), nomImage.getText(), new Date(), taReciep.getText(), 0);
        if (article == null) {
            //nomImage.getText().isEmpty()
            if (tfName.getText().isEmpty() || taDescription.getText().isEmpty() || taReciep.getText().isEmpty()) {
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            } else {
                articleService.add(newArticle);
                openInfoScreen("Added new entry successfully");
            }

        } else {
            if (tfName.getText().isEmpty() || taDescription.getText().isEmpty() || taReciep.getText().isEmpty()) {
                a.setAlertType(AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            } else {
                newArticle.setId(article.getId());
                articleService.update(newArticle);
                closeWindow(buttonAdd);
                openInfoScreen("Article updated successfully");
            }

        }
    }

    void closeWindow(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    void openInfoScreen(String message) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InfoScreenArticle.fxml"));
            root = (Parent) fxmlLoader.load();
            InfoScreenArticleController infoScreenArticleController = fxmlLoader.<InfoScreenArticleController>getController();
            infoScreenArticleController.setMessage(message);
            Stage stage = new Stage();
            stage.setTitle("Confirmation");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
