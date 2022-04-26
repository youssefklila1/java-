/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.ThematiqueService;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class AjouterThematiqueController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouterThematique(ActionEvent event) throws IOException {
     Thematique t = new Thematique(tfNom.getText(),tfImage.getText());
     ThematiqueService ts = new ThematiqueService();
     ts.ajouterThematique2(t);
     Alert a = new Alert(Alert.AlertType.INFORMATION,"Thematique added",ButtonType.CANCEL);
     a.show();
     
     FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherThematique.fxml")); //jebt el fichier fxml 
       Parent root=loader.load(); //loaditou 
       Scene scene = new Scene(root); //jebt les acteurs w hatithom fi scene 
     tfNom.getScene().setRoot(root);
     /*
     AfficherThematiqueController apc = loader.getController();
     apc.setNom(tfNom.getText());
     apc.setImage(tfImage.getText());*/
    }
    
}
