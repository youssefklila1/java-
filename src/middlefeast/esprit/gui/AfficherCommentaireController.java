/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import middlefeast.esprit.entities.Commentaire;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.CommentaireService;
import middlefeast.esprit.services.DiscussionService;
import middlefeast.esprit.services.PdfService;
import middlefeast.esprit.services.ThematiqueService;
import middlefeast.esprit.tableViewModel.CommentaireModel;
import middlefeast.esprit.tableViewModel.DiscussionModel;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class AfficherCommentaireController implements Initializable {

    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_titre;

    @FXML
    private ComboBox<String> Combo_discussion;

    @FXML
    private TextField txt_parent;

    @FXML
    private TextField txt_like;

    @FXML
    private TableView<CommentaireModel> table_commentaire;

    @FXML
    private TableColumn<CommentaireModel, Integer> col_id;

    @FXML
    private TableColumn<CommentaireModel, String> col_description;
    @FXML
    private TableColumn<CommentaireModel, String> col_discussion;
    @FXML
    private TableColumn<CommentaireModel, Integer> col_parent;

    @FXML
    private TableColumn<CommentaireModel, Integer> col_like;

    @FXML
    private TextField txt_search;
    @FXML
    private ComboBox<String> combo_filtre;
    
    int index = -1;
    CommentaireService cs;
    ObservableList<CommentaireModel> listCM= FXCollections.observableArrayList();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       cs= new CommentaireService();
       updateCombo();
       updateTable();
        /*Alert a = new Alert(Alert.AlertType.INFORMATION,"Thematique in",ButtonType.CANCEL);
        a.show();*/
        
        
    }
    public void updateCombo(){
        DiscussionService ths=new DiscussionService();
        List<Discussion> list = ths.afficherDiscussion();
        for (Discussion th : list){
            Combo_discussion.getItems().add(th.getTitre());
            combo_filtre.getItems().add(th.getTitre());
        }
    }
    public void updateTable(){
        listCM= FXCollections.observableArrayList();
        col_id.setCellValueFactory(new PropertyValueFactory<CommentaireModel,Integer>("id"));
        col_description.setCellValueFactory(new PropertyValueFactory<CommentaireModel,String>("description"));
        col_discussion.setCellValueFactory(new PropertyValueFactory<CommentaireModel,String>("discussion"));
        col_parent.setCellValueFactory(new PropertyValueFactory<CommentaireModel,Integer>("parent"));
        col_like.setCellValueFactory(new PropertyValueFactory<CommentaireModel,Integer>("nblike"));
        
        for ( Commentaire c  : cs.afficherCommentaire()){
            //System.out.println(c);
            CommentaireModel cm = new  CommentaireModel(c.getId(), c.getDiscussion().getTitre(), c.getDescription(), c.getParent(), c.getNblike());
            listCM.add(cm);
        }
        table_commentaire.setItems(listCM);

    }
    
    @FXML
    void getSelected(MouseEvent event){
        index=table_commentaire.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_titre.setText(col_description.getCellData(index).toString());
        txt_parent.setText(col_parent.getCellData(index).toString());
        txt_like.setText(col_like.getCellData(index).toString());
        Combo_discussion.setValue(col_discussion.getCellData(index).toString());
        
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
                DiscussionService ths = new DiscussionService();
                List<Discussion> lth= ths.rechercheDiscussion(Combo_discussion.getValue().toString());
                Discussion t = lth.get(0);
                Commentaire d = new Commentaire(t, txt_titre.getText(),Integer.parseInt(txt_parent.getText()));
                cs.ajouterCommentaire2(d);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Commentaire Ajouté avec succès",ButtonType.OK);
                a.show();
                updateTable();
            }
        }
    }

    @FXML
    void btnModifierAction(ActionEvent event) {
       if (txt_titre.getText() == "") {
            Alert a = new Alert(Alert.AlertType.ERROR, "Le champs discussion est obligatoire", ButtonType.OK);
            a.show();
        } else {
            if (txt_titre.getText().length() > 100) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Le champs discussion ne doit pas dépasser les 100 caractères", ButtonType.OK);
                a.show();
            } else {
                DiscussionService ths = new DiscussionService();
                List<Discussion> lth= ths.rechercheDiscussion(Combo_discussion.getValue().toString());
                Discussion t = lth.get(0);
                
                Commentaire d = new Commentaire(Integer.parseInt(txt_id.getText()), t, txt_titre.getText(), Integer.parseInt(txt_parent.getText()), Integer.parseInt(txt_like.getText()));
                System.out.println(d);
                cs.modifier(d);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Discussion Modifié avec succès", ButtonType.OK);
                a.show();
                updateTable();
            }
        }
    }

    @FXML
    void btnSupprimerAction(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Voulez vous supprimer cette discussion");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK) {
            cs.supprimer(Integer.parseInt(txt_id.getText()));
            updateTable();
            txt_id.setText("");
            txt_titre.setText("");
            txt_parent.setText("");
            txt_like.setText("");
            Combo_discussion.setValue("");
        }
    }
    @FXML
    void btnSearchAction(){
      
        if(txt_search.getText().length()>0){
            //System.out.println(txt_search.getText());
           listCM=FXCollections.observableArrayList();
           for(Commentaire c : cs.rechercheCommentaire(txt_search.getText())){
              //System.out.println(d);
               listCM.add(new CommentaireModel(c.getId(),c.getDiscussion().getTitre(),c.getDescription(),c.getParent(),c.getNblike()));
           }
           table_commentaire.setItems(listCM);
        }
    }
    @FXML
    void onchangeDiscussion(){
        //System.out.print(combo_filtre.getValue());
        DiscussionService ths = new DiscussionService();
        List<Discussion> lth= ths.rechercheDiscussion(combo_filtre.getValue().toString());
        Discussion t = lth.get(0);
        listCM= FXCollections.observableArrayList();
        for ( Commentaire c  : cs.afficherCommentaireByDiscussion(t.getId())){
            //System.out.println(c);
            CommentaireModel cm = new  CommentaireModel(c.getId(), c.getDiscussion().getTitre(), c.getDescription(), c.getParent(), c.getNblike());
            listCM.add(cm);
        }
        table_commentaire.setItems(listCM);
        
    }

    @FXML
    private void pdf(MouseEvent event) {
        try {
            PdfService a = new PdfService();
            a.liste_CommandePDF();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AfficherCommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(AfficherCommentaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

    

