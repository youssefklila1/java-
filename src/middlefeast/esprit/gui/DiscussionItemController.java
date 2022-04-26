/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import middlefeast.esprit.entities.Discussion;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class DiscussionItemController implements Initializable {

    @FXML
    private AnchorPane thematiqueItem;
    @FXML
    private Label id_discussion;
    @FXML
    private Label lbl_discussion;
    @FXML
    private Button btn_voircommentaire;
private Parent root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    public void setData(Discussion d){
        //System.out.println("initData"+t.getNom());
        lbl_discussion.setText(d.getTitre());
        id_discussion.setText(String.valueOf(d.getId()));
        btn_voircommentaire.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                try { 
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Commentaire.fxml"));
            root= fxmlLoader.load();
            CommentaireController controller = fxmlLoader.getController();
            System.out.println("Hello World! "+d);
            controller.setdiscussionId(d.getId());
            controller.loadData();
            Stage window = (Stage) id_discussion.getScene().getWindow();
            window.setScene(new Scene(root));
            /*
            Stage stage= new Stage();
            stage.setScene(new Scene(root));
            stage.show();*/
        } catch (IOException ex) {
            Logger.getLogger(ThematiqueItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }
        );
    }
    
    
}
