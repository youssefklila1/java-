/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeast.esprit.gui;

//org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import middlefeast.esprit.entities.Thematique;
import middlefeast.esprit.services.ThematiqueService;


/**
 * FXML Controller class
 *
 * @author eyaba
 */
public class AfficherThematiqueController implements Initializable {
    
    @FXML
    private TextField txt_id;

    @FXML
    private TextField txt_nom;

    @FXML
    private TableView<Thematique> table_th;

    @FXML
    private TableColumn<Thematique, Integer> col_id;

    @FXML
    private TableColumn<Thematique, String> col_nom;

    @FXML
    private TableColumn<Thematique, String> col_image;
    @FXML
    private Label lbl_image;
    @FXML
    private TextField txt_search;
    @FXML
    private ImageView image;
    
    ObservableList<Thematique> listTH;
    ThematiqueService ths;
    
    int index=-1;
    File selectedFile;
 
    private String path;
    @FXML
    private TableColumn<?, ?> col_nom1;
    @FXML
    private TextField rech;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
       updateTable();
        /*Alert a = 
       new Alert(Alert.AlertType.INFORMATION,"Thematique in",ButtonType.CANCEL);
        a.show();*/
        
       
    } 
    @FXML
    void getSelected(MouseEvent event){
        index=table_th.getSelectionModel().getSelectedIndex();
        if(index <= -1){
            return;
        }
        txt_id.setText(col_id.getCellData(index).toString());
        txt_nom.setText(col_nom.getCellData(index).toString());
        lbl_image.setText(col_image.getCellData(index).toString());
        image.setImage(new Image("file:\\\\\\C:\\Users\\eyaba\\Desktop\\ImageMiddlefeast\\"+col_image.getCellData(index).toString()));
        image.setFitHeight(150);
        image.setFitWidth(160);
        
        
    }
    public void updateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<Thematique,Integer>("id"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Thematique,String>("nom"));
        col_image.setCellValueFactory(new PropertyValueFactory<Thematique,String>("image"));
        
        ths= new ThematiqueService();
        listTH = ths.afficherThematique();
        table_th.setItems(listTH);
        searchEven();
    }
    @FXML
    void btnAjouterAction(){
        if(txt_nom.getText()==""){
            Alert a = new Alert(Alert.AlertType.ERROR,"Le champs thematique est obligatoire",ButtonType.OK);
            a.show();
        }else{
            if(txt_nom.getText().length() > 30){
                Alert a = new Alert(Alert.AlertType.ERROR,"Le champs thematique ne doit pas dépasser les 20 caractères",ButtonType.OK);
                a.show();
            }else{
                if (lbl_image.getText() == "") {
                Alert a = new Alert(Alert.AlertType.ERROR,"L'image est obligatoire",ButtonType.OK);
            a.show();
                }
                else{
                Thematique t = new Thematique(txt_nom.getText(), lbl_image.getText());
                ths.ajouterThematique2(t);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Thematique Ajouter avec succès",ButtonType.OK);
                a.show();
                updateTable();
            }
        }
    } }
    @FXML
    void btnModifierAction(){
        if(txt_nom.getText().equals("")){
            Alert a = new Alert(Alert.AlertType.ERROR,"Le champs thematique est obligatoire",ButtonType.OK);
            a.show();
        }else{
            if(txt_nom.getText().length() > 30){
                Alert a = new Alert(Alert.AlertType.ERROR,"Le champs thematique ne doit pas dépasser les 20 caractères",ButtonType.OK);
                a.show();
            }else{
                //System.out.println(txt_id.getText()+"|"+txt_nom.getText()+"|"+lbl_image.getText());
                Thematique t = new Thematique(Integer.parseInt(txt_id.getText()),txt_nom.getText(), lbl_image.getText());
                ths.modifier(t);
                Alert a = new Alert(Alert.AlertType.INFORMATION,"Thematique Modifié avec succès",ButtonType.OK);
                a.show();
                updateTable();
            }
        }
    }
    @FXML
    void btnSupprimerAction(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,"Voulez vous supprimer cette thématique");
        Optional<ButtonType> option = a.showAndWait();
        if (option.get() == ButtonType.OK) {
            ths.supprimer(Integer.parseInt(txt_id.getText()));
            updateTable();
            txt_id.setText("");
            txt_nom.setText("");
            lbl_image.setText("");
        }
        //a.show();
    }
    @FXML
    void btnSearchAction(){
        if(txt_search.getText().length()>0){
            System.out.println(txt_search.getText());
           listTH=FXCollections.observableArrayList();
           for(Thematique th : ths.rechercheThematique(txt_search.getText())){
               System.out.println(th);
               listTH.add(th);
           }
           table_th.setItems(listTH);
        }
    }
    @FXML
    public void chooseFile() throws MalformedURLException{
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", ".jpg", ".png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            path = selectedFile.getName();
//                path = selectedFile.toURI().toURL().toExternalForm();

            lbl_image.setText(selectedFile.getName());
            //System.out.println(selectedFile.toURI().toString());
            image.setImage(new Image(selectedFile.toURI().toURL().toString()));
            image.setFitHeight(150);
            image.setFitWidth(160);
        

        }
    }
   
     
    public void searchEven(){
       
         ThematiqueService eve = new ThematiqueService();
         ObservableList <Thematique> l = eve.afficherThematique();        
         try{
        
          table_th.setItems(l);
          FilteredList<Thematique> f = new FilteredList<>(l, b -> true);
          rech.textProperty().addListener((ObservableValue<? extends String> observable, String olValue, String newValue)->{
             f.setPredicate(new Predicate<Thematique>() {
                 public boolean test(Thematique person) {
                     if(newValue == null|| newValue.isEmpty()){
                         return true;
                     }
                     String lowerCaseFilter= newValue.toLowerCase();
                     
                     if(String.valueOf(person.getNom()).toLowerCase().contains(lowerCaseFilter)){
                         return true;
                     }
                     else if(String.valueOf(person.getId()).indexOf(lowerCaseFilter)!=-1)
                         return true;
                     else
                         return false;
                 }

             
             });
             });
         SortedList<Thematique> sortedData = new SortedList<>(f);
         sortedData.comparatorProperty().bind(table_th.comparatorProperty());
         table_th.setItems(sortedData);

         }catch(Exception e){
             System.out.println(e.getMessage());
                 }  
       
    
   
     }
    
    
    
    
    
}
