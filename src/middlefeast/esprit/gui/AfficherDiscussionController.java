/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.DiscussionService;
import middlefeast.esprit.services.ThematiqueService;
import middlefeast.esprit.tableViewModel.DiscussionModel;

/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class AfficherDiscussionController implements Initializable {

    @FXML
    private TextField txt_id_discussion;

    @FXML
    private TextField txt_titre;

    @FXML
    private ComboBox<String> Combo_thematique;


    @FXML
    private TableView<DiscussionModel> table_discussion;

    @FXML
    private TableColumn<DiscussionModel, Integer> col_id;

    @FXML
    private TableColumn<DiscussionModel, String> col_titre;

    @FXML
    private TableColumn<DiscussionModel, String> col_thematique;
    @FXML
    private TextField txt_search;
    
    @FXML
    private ComboBox<String> combo_filtre;
    
    int index = -1;
    DiscussionService ds;
    ObservableList<DiscussionModel> listDS= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ds= new DiscussionService();
       updateCombo();
       updateTable();
        /*Alert a = new Alert(Alert.AlertType.INFORMATION,"Thematique in",ButtonType.CANCEL);
        a.show();*/
        
        
    }
    public void updateCombo(){
        ThematiqueService ths=new ThematiqueService();
        ObservableList<Thematique> listTH= ths.afficherThematique();
        for (Thematique th : listTH){
            Combo_thematique.getItems().add(th.getNom());
            
            combo_filtre.getItems().add(th.getNom());
        }
    }
    public void updateTable(){
        listDS= FXCollections.observableArrayList();
        col_id.setCellValueFactory(new PropertyValueFactory<DiscussionModel,Integer>("id"));
        col_titre.setCellValueFactory(new PropertyValueFactory<DiscussionModel,String>("titre"));
        col_thematique.setCellValueFactory(new PropertyValueFactory<DiscussionModel,String>("thematique"));
        
        for ( Discussion d  : ds.afficherDiscussion()){
            //System.out.println(d.getId()+" "+d.getThematique().getNom()+" "+ d.getTitre());
            DiscussionModel dm = new  DiscussionModel(d.getId(),d.getThematique().getNom(),d.getTitre());
            listDS.add(dm);
        }
        table_discussion.setItems(listDS);

    }
    
    @FXML
    void getSelected(MouseEvent event){
        index=table_discussion.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_id_discussion.setText(col_id.getCellData(index).toString());
        txt_titre.setText(col_titre.getCellData(index).toString());
        Combo_thematique.setValue(col_thematique.getCellData(index).toString());
        
    }
    @FXML
    void btnAjouterAction(ActionEvent event) {
        if(txt_titre.getText()==""){
            Alert a = new Alert(Alert.AlertType.ERROR,"Le champs discussion est obligatoire",ButtonType.OK);
            a.show();
        }else{
            if(txt_titre.getText().length() > 40){
                Alert a = new Alert(Alert.AlertType.ERROR,"Le champs discussion ne doit pas dépasser les 40 caractères",ButtonType.OK);
                a.show();
            }
            else{
                ThematiqueService ths = new ThematiqueService();
                List<Thematique> lth= ths.rechercheThematique(Combo_thematique.getValue().toString());
                Thematique t = lth.get(0);
                Discussion d = new Discussion(t, txt_titre.getText());
                ds.ajouterDiscussion2(d);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Discussion Ajouté avec succès",ButtonType.OK);
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
            if (txt_titre.getText().length() > 40) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Le champs discussion ne doit pas dépasser les 40 caractères", ButtonType.OK);
                a.show();
            } else {
                ThematiqueService ths = new ThematiqueService();
                List<Thematique> lth = ths.rechercheThematique(Combo_thematique.getValue().toString());
                Thematique t = lth.get(0);
                //System.out.println(t);
                Discussion d = new Discussion(Integer.parseInt(txt_id_discussion.getText()),t, txt_titre.getText());
                ds.modifier(d);
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
            ds.supprimer(Integer.parseInt(txt_id_discussion.getText()));
            updateTable();
            txt_id_discussion.setText("");
            txt_titre.setText("");
            Combo_thematique.setValue("");
        }
    }
    @FXML
    void btnSearchAction(){
        if(txt_search.getText().length()>0){
            System.out.println(txt_search.getText());
           listDS=FXCollections.observableArrayList();
           for(Discussion d : ds.rechercheDiscussion(txt_search.getText())){
              //System.out.println(d);
               listDS.add(new DiscussionModel(d.getId(),d.getThematique().getNom(),d.getTitre()));
           }
           table_discussion.setItems(listDS);
        }
    }
    
    @FXML
    void onchangeThematique(){
        System.out.print(combo_filtre.getValue());
        ThematiqueService ths = new ThematiqueService();
        List<Thematique> lth = ths.rechercheThematique(combo_filtre.getValue().toString());
        Thematique t = lth.get(0);
        listDS= FXCollections.observableArrayList();
        for ( Discussion d  : ds.afficherDiscussionByTheme(t.getId())){
            //System.out.println(d.getId()+" "+d.getThematique().getNom()+" "+ d.getTitre());
            DiscussionModel dm = new  DiscussionModel(d.getId(),d.getThematique().getNom(),d.getTitre());
            listDS.add(dm);
        }
        table_discussion.setItems(listDS);
        
    }

}

    

