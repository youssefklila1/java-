/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Commande;
import Entities.Panier;
import static GUI.CardpaymentController.showAlert;
import Services.CommandeService;
import Services.PanierService;
import Services.TutorialService;
import Tools.Smsapi;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class PanierController implements Initializable {

    @FXML
    private AnchorPane DashboardUtilis;
    @FXML
    private Button btnPanier;
    @FXML
    private Button btnCustomers;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlMenus;
    @FXML
    private Pane pnlOverview;
    @FXML
    private TextField produitfid;
    @FXML
    private TextField quantitefid;
    @FXML
    private Button btnsubmit;
    @FXML
    private Label currentTimeTF;
    @FXML
    private TableColumn<Panier, String> tutorial_col;
    @FXML
    private TableColumn<Panier, Integer> quantite_col;
    @FXML
    private TableColumn<?, ?> total_col;
    @FXML
    private TableColumn<Panier, String> action_col;
    @FXML
    private TableView<Panier> tableviewpanier;
    ObservableList<Panier> PanierList = FXCollections.observableArrayList();

    Panier panier = null;
    ObservableList<Panier> data = FXCollections.observableArrayList();
    PanierService ps = new PanierService();
    CommandeService cs = new CommandeService();
    @FXML
    private Button paiementfid;
    private TableColumn<Panier, Integer> action_col1;
    @FXML
    private Label totalpanier;
    @FXML
    private VBox pnItems;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnStats;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // refreshlist() ;
        tableviewpanier.setEditable(true);
        tableviewpanier.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        double count = ps.sommePanier(1);
        totalpanier.setText(String.valueOf(count));
        loadData();
        initClock();

    }

    @FXML
    private void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnPanier) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Panier.fxml"));
            DashboardUtilis.getChildren().setAll(panee);
        }
        if (actionEvent.getSource() == btnCustomers) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Commande.fxml"));
            DashboardUtilis.getChildren().setAll(panee);
        }
        if (actionEvent.getSource() == btnStats) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Statistique.fxml"));
            DashboardUtilis.getChildren().setAll(panee);
        }
        if (actionEvent.getSource() == btnMenus) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("CommandeFront.fxml"));
            DashboardUtilis.getChildren().setAll(panee);
        }
    }

    public void refreshlist() {
        data.clear();
        data = FXCollections.observableArrayList(ps.getPanier(1));
        tutorial_col.setCellValueFactory(new PropertyValueFactory<>("tutorial_id"));
        quantite_col.setCellValueFactory(new PropertyValueFactory<>("qte"));

        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        double count = ps.sommePanier(1);
        totalpanier.setText(String.valueOf(count));
        tableviewpanier.setItems(data);
    }

    private void loadData() {
        TutorialService ts = new TutorialService();
        data.clear();
        data = FXCollections.observableArrayList(ps.getPanier(1));
        tutorial_col.setCellValueFactory(new PropertyValueFactory<>("tutorial_id"));
        tutorial_col.setCellValueFactory(cellData -> new SimpleStringProperty(ts.getTutoriel(cellData.getValue().getTutorial_id())));
        int prix = ts.getTutorielprix(2); // yelzem nrecuperi id tutorial 
        quantite_col.setCellValueFactory(new PropertyValueFactory<>("qte"));
        quantite_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantite_col.setOnEditCommit(new EventHandler<CellEditEvent<Panier, Integer>>() {
            @Override
            public void handle(CellEditEvent<Panier, Integer> event) {
                Panier person = event.getRowValue();
                person.setUser_id(event.getNewValue());
                ps.modifierPanierqte(event.getNewValue(), person.getId(), event.getNewValue() * prix);
                loadData();
            }
        });

        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableviewpanier.setItems(data);
        double count = ps.sommePanier(1);
        totalpanier.setText(String.valueOf(count));
        //add cell of button edit 
        Callback<TableColumn<Panier, String>, TableCell<Panier, String>> cellFoctory = (TableColumn<Panier, String> param) -> {
            // make cell containing buttons
            final TableCell<Panier, String> cell = new TableCell<Panier, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                panier = tableviewpanier.getSelectionModel().getSelectedItem();
                                PanierService ps = new PanierService();
                                ps.supprimerPanier((int) panier.getId());
                                double count = ps.sommePanier(1);
                                totalpanier.setText(String.valueOf(count));
                                refreshlist();
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }

                        });

                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        action_col.setCellFactory(cellFoctory);
        tableviewpanier.setItems(data);

    }
// ignore

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            currentTimeTF.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
// add product in cart shop

    @FXML
    private void ajouter_panier(MouseEvent event) {
        StringBuilder errors = new StringBuilder();
        if (produitfid.getText().trim().isEmpty()) {
            errors.append("- Please enter a produit \n");//string s --- s+="erreur"
        }
        if (quantitefid.getText().trim().isEmpty()) {

            errors.append("- Please enter a quantity\n");
        }
        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errors");
            alert.setContentText(errors.toString());
            alert.showAndWait();
        } else {
            TutorialService ts = new TutorialService();
            int idproduit = Integer.parseInt(produitfid.getText());
            int prix = ts.getTutorielprix(idproduit);
            int quantite = Integer.parseInt(quantitefid.getText());
            Date date = new Date(System.currentTimeMillis());
            PanierService ps = new PanierService();
            int userid = 1;
            Panier p = new Panier(idproduit, userid, quantite, quantite * prix);

            ps.ajouterPanier(p);
            loadData();
            resetProductTF();
            double count = ps.sommePanier(1);
            totalpanier.setText(String.valueOf(count));

            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.POPUP;
            tray.setAnimationType(type);
            tray.setTitle("Ajout d'un produit au panier done");
            tray.setMessage("You successufuly added a product ");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(1000));
        }
    }

    // to clear fields after submit
    private void resetProductTF() {
        produitfid.setText("");
        quantitefid.setText("");

    }
// proceed command

    @FXML
    private void ajouter_commande(MouseEvent event) {
        double somme = ps.sommePanier(1);

        if (somme > 0) {

            AnchorPane panee;
            try {
                panee = FXMLLoader.load(getClass().getResource("Payment.fxml"));
                DashboardUtilis.getChildren().setAll(panee);
            } catch (IOException ex) {
                Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Veuillez remplir votre panier", "error", "Veuillez remplir votre panier");
        }
    }

    @FXML
    private void handleButtons(ActionEvent event) {
    }

}
