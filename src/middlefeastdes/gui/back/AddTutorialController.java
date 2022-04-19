package middlefeastdes.gui.back;

import middlefeastdes.gui.back.InfoScreenCourseController;
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
import middlefeastdes.service.TutorialService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class AddTutorialController implements Initializable {

    @FXML
    private Button ImageTutorial;

    @FXML
    private Button buttonAdd;

    @FXML
    private ComboBox<String> cbCategory;

    @FXML
    private TextField nomImage;

    @FXML
    private TextField nomVideo;

    @FXML
    private TextArea taDescription;

    @FXML
    private Label lblTask;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfTitle;

    @FXML
    private Button videoTutorial;

    private Tutorial tutorial;

    TutorialService tutorialService = new TutorialService();
    
    Alert a = new Alert(Alert.AlertType.NONE);


    @FXML
    private String selectImage(ActionEvent event) {
        final FileChooser image = new FileChooser();
        image.setTitle("Select an image ");
        String path = image.showOpenDialog(ImageTutorial.getScene().getWindow()).getName();
        nomImage.setText(path);
        return "http://127.0.0.1/uploads/"+path;
    }

    @FXML
    private String selectVideo(ActionEvent event) {
        final FileChooser video = new FileChooser();
        video.setTitle("Select a video ");
        String path = video.showOpenDialog(videoTutorial.getScene().getWindow()).getName();
        nomVideo.setText(path);
        return "http://127.0.0.1/uploads/"+path;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            if(tutorial != null){
                lblTask.setText("Update Tutorial");
                buttonAdd.setText("Update");
                tfTitle.setText(tutorial.getTitre());
                taDescription.setText(tutorial.getDescription());
                cbCategory.setValue(tutorial.getCategory());
                tfPrice.setText(String.valueOf(tutorial.getPrix()));
                nomImage.setText(tutorial.getImage());
                nomVideo.setText(tutorial.getVideo());
            }
        });
        cbCategory.getItems().addAll("Starters", "Main Dishes", "Desserts");
    }

    @FXML
    void ajouter(ActionEvent event) throws SQLException {
        if(tutorial == null){
            
            if(Double.parseDouble(tfPrice.getText())<=0)
            {
                  a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Price Should Be Positive ! ");
                a.show();
            }else
            
            if(tfTitle.getText().isEmpty() || taDescription.getText().isEmpty() || tfPrice.getText().isEmpty() )
            {
                  a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            }else
            {
                        Tutorial newTutorial = new Tutorial(nomVideo.getText(), nomImage.getText(), new Date(), cbCategory.getValue(), tfTitle.getText(), taDescription.getText(), Double.parseDouble(tfPrice.getText()));

                 tutorialService.add(newTutorial);
            openInfoScreen("Added new entry successfully");
            }
            
           
        }else{
               if(Double.parseDouble(tfPrice.getText())<=0)
            {
                  a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Price Should Be Positive ! ");
                a.show();
            }else
            
            if(tfTitle.getText().isEmpty() || taDescription.getText().isEmpty() || tfPrice.getText().isEmpty() )
            {
                  a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("Some Fields Are Empty Please check ! ");
                a.show();
            }else{
                        Tutorial newTutorial = new Tutorial(nomVideo.getText(), nomImage.getText(), new Date(), cbCategory.getValue(), tfTitle.getText(), taDescription.getText(), Double.parseDouble(tfPrice.getText()));

            newTutorial.setId(tutorial.getId());
            tutorialService.update(newTutorial);
            closeWindow(buttonAdd);
            openInfoScreen("Tutorial updated successfully");
            }
        }
    }

    void closeWindow(Button btn){
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    void openInfoScreen(String message){
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InfoScreenTutorial.fxml"));
            root = (Parent)fxmlLoader.load();
            InfoScreenTutorialController infoScreenTutorialController = fxmlLoader.<InfoScreenTutorialController>getController();
            infoScreenTutorialController.setMessage(message);
            Stage stage = new Stage();
            stage.setTitle("Confirmation");
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }
}
