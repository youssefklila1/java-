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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.services.DiscussionService;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class AccueilController implements Initializable {
    private Parent fxml;
     @FXML
    private AnchorPane root;
     @FXML
    private PieChart pc;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //pc=new PieChart();
        pc.setTitle("Les Discussion qui continnent les plus de commentaires");
        DiscussionService ds= new DiscussionService();
        List<Discussion> lDS= ds.afficherDiscussionPlus();
        ObservableList<PieChart.Data> ol=FXCollections.observableArrayList();
        int i=0;
        for (Discussion d:lDS){
            i++;
            if(i<=10){
            //System.out.println(d.getTitre());
            ol.add(new PieChart.Data(d.getTitre(), d.getNb_comment()));
            }
        }
        pc.setData(ol);
        
    }  
    
    @FXML
    void btnThematiquesAction(){
        try {
            fxml= FXMLLoader.load(getClass().getResource("AfficherThematique.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @FXML
    void btnDiscussionsAction(){
        try {
            fxml= FXMLLoader.load(getClass().getResource("AfficherDiscussion.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void btnCommentairesAction(){
        try {
            fxml= FXMLLoader.load(getClass().getResource("AfficherCommentaire.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
