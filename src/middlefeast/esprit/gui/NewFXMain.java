/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author eyaba
 */

public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml")); //jebt el fichier fxml 
       
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherDiscussion.fxml")); //jebt el fichier fxml 
       //FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherCommentaire.fxml")); //jebt el fichier fxml 
       Parent root=loader.load(); //loaditou 
       Scene scene = new Scene(root); //jebt les acteurs w hatithom fi scene 
       primaryStage.setScene(scene);
       primaryStage.setTitle("Gestion Forum");
       primaryStage.show();
       
     
     
      // FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherThematique.fxml"));
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
