/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.gui.back;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import middlefeastdes.entity.Course;
import middlefeastdes.entity.User;
import middlefeastdes.service.CourseService;
import middlefeastdes.service.UserService;

/**
 *
 * @author Omar
 */
public class ShowAllUsersController implements Initializable{
    
      @FXML
    private ScrollPane scrollTable;

    UserService UserService = new UserService();
    List<User> users = new ArrayList<>();

    private final ObservableList<User> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data.clear();
        try {
            GridPane grid = new GridPane();
            grid.add(new Label("E-mail"), 0, 0, 1, 1);
            grid.add(new Label("First Name"), 1, 0, 1, 1);
            grid.add(new Label("Last Name"), 2, 0, 1, 1);
            
            grid.setHgap(60);
            grid.setVgap(30);
            grid.setPadding(new Insets(10));
            grid.setStyle(
                    "-fx-background-color: #ffffff;"
            );
            users = UserService.findAll();
            data.addAll(users);
            for (int i = 0; i < data.size(); i++) {
                HBox row = new HBox();
                row.prefHeightProperty().setValue(55);
                row.alignmentProperty().setValue(Pos.CENTER_LEFT);
                row.setStyle("-fx-background-color: #ffffff");
                row.setOnMouseEntered(event -> row.setStyle("-fx-background-color: #332940"));
                row.setOnMouseExited(event -> row.setStyle("-fx-background-color: #121212"));
                Label lblDescription = new Label(data.get(i).getEmail());
                lblDescription.setTextFill(Color.BLACK);
                Label lblMode = new Label(data.get(i).getFirstname());
                lblMode.setTextFill(Color.BLACK);
                Label lblDateDebut = new Label(data.get(i).getLastname());
                lblDateDebut.setTextFill(Color.BLACK);
                grid.add(lblDescription, 0, i+1, 1, 1);
                grid.add(lblMode, 1, i+1, 1, 1);
                grid.add(lblDateDebut, 2, i+1, 1, 1);
            
                grid.add(DetailsButton(data.get(i), resources), 6, i+1, 1, 1);

                scrollTable.setContent(grid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
        
        private Button DetailsButton(User user, ResourceBundle resources) {
        Button btn = new Button("Details");
        btn.setTextFill(Color.BLACK);
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
            btn.setTextFill(Color.color(0.1647, 0.451, 1.0));
        });
        btn.setOnMouseExited(event -> {
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: #2a73ff;" +
                            "-fx-border-radius: 20;" +
                            "-fx-animated: 1000;"
            );
            btn.setTextFill(Color.BLACK);
        });
        btn.setOnAction(event -> {
            Parent root;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindUserById.fxml"));
                root = (Parent)fxmlLoader.load();
                FindUserByIdController findUserByIdController = fxmlLoader.<FindUserByIdController>getController();
                findUserByIdController.setUser(user);
                Stage stage = new Stage();
                stage.setTitle("Users infos");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return btn;
    }
    
}
