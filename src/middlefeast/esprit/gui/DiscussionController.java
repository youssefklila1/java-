/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.DiscussionService;
import middlefeast.esprit.services.ThematiqueService;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class DiscussionController implements Initializable {

    @FXML
    private Label lbl_discussion;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane grid;
    private int thematique_id;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void loadData(){
        //System.out.println("initData"+thematique_id);
        DiscussionService ths= new DiscussionService();
        List<Discussion> listTH = ths.afficherDiscussionByTheme(thematique_id);
        
        int colonne=0;
        int row=1;
        try {
            for (Discussion t : listTH) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("DiscussionItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                
                DiscussionItemController controller = fxmlLoader.getController();
                controller.setData(t);
                
                grid.add(anchorPane,colonne,row++);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);                
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);                
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));
                //break;
            }
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    
    public void setThematiqueId(int id){
        this.thematique_id=id;
    }
    
    
}
