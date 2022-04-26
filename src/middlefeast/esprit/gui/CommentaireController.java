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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import middlefeast.esprit.entities.Commentaire;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.services.CommentaireService;
import middlefeast.esprit.services.DiscussionService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class CommentaireController implements Initializable {

    @FXML
    private Label lbl_commentaire;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane grid;
    private int discussion_id;
    @FXML
    private TextField txt_titre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void setdiscussionId(int id){
        discussion_id=id;
    }
    public void loadData(){
        CommentaireService ths= new CommentaireService();
        List<Commentaire> listTH = ths.afficherCommentaireByDiscussion(discussion_id);
        
        int colonne=0;
        int row=1;
        try {
            for (Commentaire t : listTH) {
                if(t.getParent()==0){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("CommentaireItem.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                
                CommentaireItemController controller = fxmlLoader.getController();
                controller.setData(t);
                
                row++;
                grid.add(anchorPane,colonne,row++);
                grid.add(new AnchorPane(),1,row);
                for (Commentaire tc : listTH) {
                    if(t.getId()==tc.getParent()){
                        FXMLLoader fxmlLoaderc = new FXMLLoader();
                        fxmlLoaderc.setLocation(getClass().getResource("CommentaireItem.fxml"));

                        AnchorPane anchorPanec = fxmlLoaderc.load();

                        CommentaireItemController controllerc = fxmlLoaderc.getController();
                        controllerc.setData(tc);
                        grid.add(new AnchorPane(),colonne,row++);
                        grid.add(anchorPanec,1,row);
                        
                    }
                }
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);                
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);                
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                
                GridPane.setMargin(anchorPane, new Insets(12));
                
                }
                //break;
            }
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }
    @FXML
    void btnAjouterAction(ActionEvent event) {
        if(txt_titre.getText()==""){
            Alert a = new Alert(Alert.AlertType.ERROR,"Le champs description est obligatoire",ButtonType.OK);
           a.show();
        }else{
            
            
            
            if(txt_titre.getText().length() > 100){
                Alert a = new Alert(Alert.AlertType.ERROR,"Le champs description ne doit pas dépasser les 100 caractères",ButtonType.OK);
               a.show();
            }else{
                CommentaireService cs = new CommentaireService();
                DiscussionService ths = new DiscussionService();
                Discussion t= ths.detailDiscussion(discussion_id);
                
                Commentaire d = new Commentaire(t, txt_titre.getText(),0);
                cs.ajouterCommentaire2(d);
                /*
                         Alert a = new Alert(Alert.AlertType.INFORMATION,"Commentaire Ajouté avec succès",ButtonType.OK);
                a.show(); */
                loadData();
                //updateTable();
            TrayNotification tray = new TrayNotification();

            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Commmentaire ajouté avec succés");
            tray.setMessage("You successufuly signin in our application");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1000));
            }
            
        }
    }
    
}
