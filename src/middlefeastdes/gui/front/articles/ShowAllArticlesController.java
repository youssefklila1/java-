package middlefeastdes.gui.front.articles;

import middlefeastdes.entity.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import middlefeastdes.service.ArticleService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;
import middlefeastdes.gui.front.UserSession;
import middlefeastdes.service.WishlistArticleService;
import org.controlsfx.control.Rating;

public class ShowAllArticlesController implements Initializable {

    @FXML
    private ScrollPane scrollTable;

    private Rating rating;

    @FXML
    private TextField searchBox;

    ArticleService articleService = new ArticleService();
    List<Article> articles = new ArrayList<>();
        private WishlistArticleService wishlistArticleService = new WishlistArticleService();
 UserSession userSession = UserSession.getInstace();

    private final ObservableList<Article> data = FXCollections.observableArrayList();

    private ObservableList<Article> filterList(List<Article> list, String searchText) {
        List<Article> filteredList = new ArrayList<>();
        for (Article article : list) {
            if (search(article, searchText)) {
                filteredList.add(article);
            }
        }
        return FXCollections.observableArrayList(filteredList);
    }

    private boolean search(Article article, String searchText) {
        return (article.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (article.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                || Integer.toString(article.getId()).equals(searchText.toLowerCase());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        data.clear();
        try {
            GridPane grid = new GridPane();
            grid.setVgap(100);
            grid.setHgap(100);
            articles = articleService.findAll();
            if (articles.isEmpty()) {
                Pane emptyPane = new Pane();
                emptyPane.setPrefWidth(950);
                emptyPane.setPrefHeight(486);
                emptyPane.setStyle("-fx-background-color: #ffffff;");
                Label emptyLabel = new Label("No items found");
                emptyLabel.setStyle(""
                        + "-fx-text-fill: #000000;"
                        + "-fx-font-size: 17;"
                        + "-fx-font-weight: bold");
                emptyLabel.layoutXProperty().bind(emptyPane.widthProperty().subtract(emptyLabel.widthProperty()).divide(2));
                emptyLabel.layoutYProperty().bind(emptyPane.heightProperty().subtract(emptyLabel.heightProperty()).divide(2));
                emptyPane.getChildren().add(emptyLabel);
                scrollTable.setContent(emptyPane);
            } else {
//                                data.addAll(articles);
                FilteredList<Article> filteredData = new FilteredList<>(FXCollections.observableList(articleService.findAll()));

                int row = -1;
                for (int i = 0; i < filteredData.size(); i++) {
                    int col = i % 2 == 0 ? 0 : 1;
                    Image image = new Image("http://127.0.0.1/uploads/" + filteredData.get(i).getPicture(), 300, 300, true, true);
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);

                    Label lblName = new Label(filteredData.get(i).getName());
                    lblName.setStyle(""
                            + "-fx-font-weight: bold;"
                            + "-fx-font-size: 17;");
                    Label lblDescription = new Label(filteredData.get(i).getDescription());
                    lblDescription.setStyle(""
                            + "-fx-font-size: 17;");
                    VBox vBox = new VBox();
                    Article tmp = filteredData.get(i);
                    
           

                    rating = new Rating(5, articleService.getRating(tmp.getId()));

                    rating.setUpdateOnHover(false);
                    rating.setPartialRating(false);
                    rating.setDisable(true);

                    vBox.setOnMouseClicked(event -> openDetails(tmp));
                    vBox.setMaxWidth(300);
                    vBox.setMaxHeight(400);

                    vBox.getChildren().addAll(imageView, lblName, lblDescription, rating);

                    if (i % 2 == 0) {
                        row++;
                    }

                    searchBox.textProperty().addListener((observable, oldValue, newValue)
                            -> {
                        try {
                            refresh();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    });

                    grid.add(vBox, col, row);

                }

                scrollTable.setContent(grid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refresh() throws SQLException {
        
        FilteredList<Article> filteredData = new FilteredList<>(FXCollections.observableList(articleService.findAll()));
                filteredData.setPredicate(createPredicate(searchBox.getText()));

        GridPane grid = new GridPane();
        grid.setVgap(100);
        grid.setHgap(100);

        int row = -1;
        for (int i = 0; i < filteredData.size(); i++) {
            int col = i % 2 == 0 ? 0 : 1;
            Image image = new Image("http://127.0.0.1/uploads/" + filteredData.get(i).getPicture(), 300, 300, true, true);
            ImageView imageView = new ImageView();
            imageView.setImage(image);

            Label lblName = new Label(filteredData.get(i).getName());
            lblName.setStyle(""
                    + "-fx-font-weight: bold;"
                    + "-fx-font-size: 17;");
            Label lblDescription = new Label(filteredData.get(i).getDescription());
            lblDescription.setStyle(""
                    + "-fx-font-size: 17;");
            VBox vBox = new VBox();
            Article tmp = filteredData.get(i);

            rating = new Rating(5, articleService.getRating(tmp.getId()));

            rating.setUpdateOnHover(false);
            rating.setPartialRating(false);
            rating.setDisable(true);

            vBox.setOnMouseClicked(event -> openDetails(tmp));
            vBox.setMaxWidth(300);
            vBox.setMaxHeight(400);

            vBox.getChildren().addAll(imageView, lblName, lblDescription, rating);

            if (i % 2 == 0) {
                row++;
            }

            searchBox.textProperty().addListener((observable, oldValue, newValue)
                    -> {
                filteredData.setPredicate(createPredicate(newValue));
            });

            grid.add(vBox, col, row);

        }

        scrollTable.setContent(grid);
    }

    private Predicate<Article> createPredicate(String searchText) {
        return article -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return search(article, searchText);
        };
    }

    private void openDetails(Article article) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../gui/front/articles/FindArticleById.fxml"));
            root = (Parent) fxmlLoader.load();
            FindArticleByIdController findArticleByIdController = fxmlLoader.<FindArticleByIdController>getController();
                        try {
                            System.out.println(article);
            wishlistArticleService.findAll().forEach((t) -> {
                System.out.println("Hello forach");
                System.out.println(t);
                System.out.println("is Equal : "+(t.getArticle_id() == article.getId()));
                if (t.getArticle_id() == article.getId()) {
                    System.out.println(" Article found in wishlist");
                    article.setFavid(t.getId());
                  
                   
                }

            });
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            findArticleByIdController.setArticle(article);
            Stage stage = new Stage();
            stage.setTitle("Article infos");
            stage.resizableProperty().setValue(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
