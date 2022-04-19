package middlefeastdes.gui.back;

import middlefeastdes.gui.back.AddCourseController;
import middlefeastdes.entity.Tutorial;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class FindTutorialByIdController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private ImageView imgDisplay;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTitle;

    @FXML
    private MediaView videoDisplay;

    private Tutorial tutorial;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Image image = new Image("http://127.0.0.1/uploads/"+tutorial.getImage());
            imgDisplay.setImage(image);
            Media video = new Media("http://127.0.0.1/uploads/"+tutorial.getVideo());
            MediaPlayer player = new MediaPlayer(video);
            player.play();
            videoDisplay.setMediaPlayer(player);
            lblDescription.setText(tutorial.getDescription());
            lblTitle.setText(tutorial.getTitre());
            lblPrice.setText(String.valueOf(tutorial.getPrix()));
            lblCategory.setText(tutorial.getCategory());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDate.setText(simpleDateFormat.format(tutorial.getDateTuto()));
        });
    }

    @FXML
    void deleteTutorial(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConfirmDeleteTutorial.fxml"));
            root = (Parent)fxmlLoader.load();
            ConfirmDeleteTutorialController confirmDeleteTutorialController = fxmlLoader.<ConfirmDeleteTutorialController>getController();
            confirmDeleteTutorialController.setTutorial(tutorial);
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
    void updateTutorial(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddTutorial.fxml"));
        root = (Parent) fxmlLoader.load();
        AddTutorialController addTutorialController = fxmlLoader.<AddTutorialController>getController();
        addTutorialController.setTutorial(tutorial);
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

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }
}
