package middlefeastdes.gui.back;

import middlefeastdes.entity.Tutorial;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import middlefeastdes.service.TutorialService;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmDeleteTutorialController {

    private Tutorial tutorial;
    private TutorialService tutorialService = new TutorialService();


    @FXML
    private Button btnReturn;

    @FXML
    private Button btnDelete;

    @FXML
    void returnToPrevious(ActionEvent event) {
        Parent root;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindTutorialById.fxml"));
            root = (Parent)fxmlLoader.load();
            FindTutorialByIdController findTutorialByIdController = fxmlLoader.<FindTutorialByIdController>getController();
            findTutorialByIdController.setTutorial(tutorial);
            Stage stage = new Stage();
            stage.setTitle("Courses infos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeWindow(btnReturn);
    }

    @FXML
    void deleteCourse(ActionEvent event) throws SQLException {
        tutorialService.delete(tutorial.getId());
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
