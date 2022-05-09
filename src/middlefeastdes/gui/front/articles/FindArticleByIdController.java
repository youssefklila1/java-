package middlefeastdes.gui.front.articles;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import middlefeastdes.entity.WishlistArticle;
import middlefeastdes.gui.front.UserSession;
import middlefeastdes.gui.front.wishlists.ConfirmDeleteFromWishlistController;
import middlefeastdes.service.ArticleService;
import middlefeastdes.service.WishlistArticleService;
import org.controlsfx.control.*;

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

    @FXML
    private Button btnFav;

    @FXML
    private Rating rating;


    Boolean exist = false;

    private Article article;
    private WishlistArticleService wishlistArticleService = new WishlistArticleService();
    ArticleService articleService = new ArticleService();
    UserSession userSession = UserSession.getInstace();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        

        try {
            if (wishlistArticleService.findByUser(userSession.getConnectedUser()).contains(article)) {
                btnFav.setVisible(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Platform.runLater(() -> {
            try {
                if (userSession.getConnectedUser() != null) {
                    rating.setVisible(true);
                    rating.setRating(articleService.getArticleUserRate(userSession.getConnectedUser().getId(), article.getId()));
                    rating.setOnMouseClicked((event) -> {
                        try {
                            articleService.rateArticle(article.getId(), userSession.getConnectedUser().getId(), (int) rating.getRating());
                        } catch (SQLException ex) {
                            Logger.getLogger(FindArticleByIdController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                } else {
                    rating.setVisible(false);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
         

            if (article.getFavid() != 0) {

                btnFav.setText("Delete From Wishlist");
            }

            try {
                articleService.addOneView(article);

            } catch (SQLException ex) {
                Logger.getLogger(FindArticleByIdController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Image image = new Image("http://127.0.0.1/uploads/" + article.getPicture());
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
    void rate(ActionEvent event) throws SQLException {

    }

    @FXML
    void addToFav(ActionEvent event) throws IOException, SQLException {
        Parent root;
        if (article.getFavid() != 0) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../gui/front/wishlists/ConfirmDeleteFromWishlist.fxml"));
            root = (Parent) fxmlLoader.load();
            ConfirmDeleteFromWishlistController confirmDeleteFromWishlistController = fxmlLoader.<ConfirmDeleteFromWishlistController>getController();
            confirmDeleteFromWishlistController.setArticle(article);
            Stage stage = new Stage();
            stage.setTitle("Confirmation");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } else {
            if (userSession.getConnectedUser() == null) {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Please Login First !");
                a.show();
            } else {

                wishlistArticleService.add(new WishlistArticle(userSession.getConnectedUser().getId(), article.getId()));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../gui/front/articles/InfoScreenArticle.fxml"));
                root = (Parent) fxmlLoader.load();
                InfoScreenArticleController infoScreenArticleController = fxmlLoader.<InfoScreenArticleController>getController();
                infoScreenArticleController.setMessage("Article added to favorites");
                Stage stage = new Stage();
                stage.setTitle("Info");
                stage.setScene(new Scene(root));
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }
        }
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
