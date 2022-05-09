package middlefeastdes.gui.front.wishlists;

import middlefeastdes.gui.front.articles.FindArticleByIdController;
import middlefeastdes.entity.Article;
import middlefeastdes.entity.User;
import middlefeastdes.entity.WishlistArticle;
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
import middlefeastdes.service.ArticleService;
import middlefeastdes.service.WishlistArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import middlefeastdes.gui.front.UserSession;

public class ShowAllWishlistsController implements Initializable {

    
    UserSession userSession = UserSession.getInstace();
    
    @FXML
    private ScrollPane scrollTable;

    WishlistArticleService wishlistArticleService = new WishlistArticleService();
    ArticleService articleService = new ArticleService();
    List<Article> articles = new ArrayList<>();
    List<WishlistArticle> wishlistArticles = new ArrayList<>();

    private final ObservableList<Article> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data.clear();
        try {
            GridPane grid = new GridPane();
            grid.add(new Label("Name"), 0, 0, 1, 1);
            grid.add(new Label("Description"), 1, 0, 1, 1);
            grid.add(new Label("Date"), 2, 0, 1, 1);
            grid.add(new Label("Views"), 3, 0, 1, 1);
            grid.add(new Label("Details"), 4, 0, 1, 1);
            grid.setHgap(60);
            grid.setVgap(30);
            grid.setPadding(new Insets(10));
            grid.setStyle(
                    "-fx-background-color: #ffffff;"
            );
           
            articles = wishlistArticleService.findByUser(userSession.getConnectedUser());
            if(articles.isEmpty()){
                Pane emptyPane = new Pane();
                emptyPane.setPrefWidth(920);
                emptyPane.setPrefHeight(486);
                emptyPane.setStyle("-fx-background-color: #ffffff;");
                Label emptyLabel = new Label("No items found");
                emptyLabel.setStyle("" +
                        "-fx-font-size: 17;" +
                        "-fx-font-weight: bold");
                emptyLabel.layoutXProperty().bind(emptyPane.widthProperty().subtract(emptyLabel.widthProperty()).divide(2));
                emptyLabel.layoutYProperty().bind(emptyPane.heightProperty().subtract(emptyLabel.heightProperty()).divide(2));
                emptyPane.getChildren().add(emptyLabel);
                scrollTable.setContent(emptyPane);
            }else {
                data.addAll(articles);
                for (int i = 0; i < data.size(); i++) {
                    Label lblName = new Label(data.get(i).getName());
                    Label lblDescription = new Label(data.get(i).getDescription());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Label lblDate = new Label(simpleDateFormat.format(data.get(i).getDate()));
                    Label lblViews = new Label(String.valueOf(data.get(i).getVues()));
                    grid.add(lblName, 0, i + 1, 1, 1);
                    grid.add(lblDescription, 1, i + 1, 1, 1);
                    grid.add(lblDate, 2, i + 1, 1, 1);
                    grid.add(lblViews, 3, i + 1, 1, 1);
                    grid.add(DetailsButton(data.get(i)), 4, i + 1, 1, 1);

                    scrollTable.setContent(grid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Button DetailsButton(Article article) {
        Button btn = new Button("Details");
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
        });
        btn.setOnMouseExited(event -> {
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: #2a73ff;" +
                            "-fx-border-radius: 20;" +
                            "-fx-animated: 1000;"
            );
        });
        btn.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../gui/front/articles/FindArticleById.fxml"));
                root = (Parent)fxmlLoader.load();
                FindArticleByIdController findArticleByIdController = fxmlLoader.<FindArticleByIdController>getController();
                findArticleByIdController.setArticle(article);
                Stage stage = new Stage();
                stage.setTitle("Article infos");
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
