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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import middlefeast.esprit.entities.Commentaire;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.services.CommentaireService;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class CommentaireItemController implements Initializable {

    @FXML
    private AnchorPane thematiqueItem;
    @FXML
    private Label id_commentaire;
    @FXML
    private Label lbl_commentaire;
    @FXML
    private Label nb_like;
    @FXML
    private ImageView btn_like;
    @FXML 
    private ImageView btn_repondre;
    @FXML 
    private TextField txt_reponse;
    
    
    
    
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    public void setData(Commentaire c){
        lbl_commentaire.setText(c.getDescription());
        if(c.getParent()!=0){
            lbl_commentaire.setBackground(new  Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
            btn_repondre.setVisible(false);
            txt_reponse.setVisible(false);
        }
        id_commentaire.setText(String.valueOf(c.getId()));
        nb_like.setText(String.valueOf(c.getNblike()));
        btn_like.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
                
                CommentaireService ths= new CommentaireService();
                    ths.like(c.getId());
                    Commentaire c2 = ths.detailCommentaire(c.getId());
                    nb_like.setText(String.valueOf(c2.getNblike()));
                    
                
            }
        }
        );
        btn_repondre.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
                CommentaireService ths= new CommentaireService();
                Discussion d= c.getDiscussion();
                Commentaire cr = new Commentaire(d, txt_reponse.getText());
                ths.repondreCommentaire(cr,c.getId());
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Commentaire Ajouté avec succès",ButtonType.OK);
                a.show();  
                
                try { 
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Commentaire.fxml"));
                    Parent root= fxmlLoader.load();
                    CommentaireController controller = fxmlLoader.getController();
                    controller.setdiscussionId(c.getDiscussion().getId());
                    controller.loadData();
                    Stage window = (Stage) id_commentaire.getScene().getWindow();
                    window.setScene(new Scene(root));
                    /*
                    Stage stage= new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();*/
                } catch (IOException ex) {
                    Logger.getLogger(ThematiqueItemController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        /*btn_repondre.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
                CommentaireService ths= new CommentaireService();
                Discussion d= c.getDiscussion();
                Commentaire cr = new Commentaire(d, txt_reponse.getText());
                ths.repondreCommentaire(cr,c.getId());
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Commentaire Ajouté avec succès",ButtonType.OK);
                a.show();  
                
                try { 
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Commentaire.fxml"));
                    Parent root= fxmlLoader.load();
                    CommentaireController controller = fxmlLoader.getController();
                    controller.setdiscussionId(c.getDiscussion().getId());
                    controller.loadData();
                    Stage window = (Stage) id_commentaire.getScene().getWindow();
                    window.setScene(new Scene(root));
                    
                } catch (IOException ex) {
                    Logger.getLogger(ThematiqueItemController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        );*/
    }
    
    
}
