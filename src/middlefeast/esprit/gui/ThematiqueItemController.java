/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.xml.ws.Action;
import middlefeast.esprit.entities.Thematique;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class ThematiqueItemController  {

    @FXML
    private AnchorPane thematiqueItem;
    @FXML
    private Label lbl_thematique;
    @FXML
    private ImageView image_thematique;
    @FXML
    private Button btn_voirdiscussion;
    @FXML
    private Label id_thematique;
    private Parent root;
       
    
    public void setData(Thematique t){
        
        lbl_thematique.setText(t.getNom());
        image_thematique.setImage(new Image("file:\\\\\\C:\\Users\\eyaba\\Desktop\\ImageMiddlefeast\\"+t.getImage()));
        id_thematique.setText(String.valueOf(t.getId()));
        btn_voirdiscussion.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Discussion.fxml"));
                    root = fxmlLoader.load();
                    System.out.println("Hello World! "+t);

                    DiscussionController controller = fxmlLoader.getController();
                    
                    controller.setThematiqueId(t.getId());
                    controller.loadData();
                    /*Stage window = (Stage) id_thematique.getScene().getWindow();
                    window.setScene(new Scene(root));*/
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(ThematiqueItemController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        
    }
}