package middlefeastdes.gui.back;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoScreenArticleController implements Initializable {
    @FXML
    private Button btnOk;

    @FXML
    private Label infoMessage;

    private String message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            infoMessage.setText(message);
        });
    }

    @FXML
    void confirm(ActionEvent event) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
