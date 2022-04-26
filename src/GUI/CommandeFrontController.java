package GUI;

import Entities.Commande;
import Services.CommandeService;
import Services.PdfCommande;
import com.itextpdf.text.DocumentException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class CommandeFrontController implements Initializable {

    @FXML
    private VBox pnItems = null;

    private Button btnOrders;

    @FXML
    private Button btnPanier;

    @FXML
    private Button btnMenus;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private ComboBox<String> combotri;

    ObservableList<String> ss = FXCollections.observableArrayList();

    private Button btnReclamation;
    @FXML
    private TextField recherchetf;

    @FXML
    private Button btntri;
    @FXML
    private AnchorPane DashboardUtilis;
    @FXML
    private Label currentTimeTF;
    @FXML
    private TableView<Commande> tableviewcommande;
    @FXML
    private TableColumn<Commande, Double> total_col;
    @FXML
    private TableColumn<Commande, String> etat_col;
    ObservableList<Commande> data = FXCollections.observableArrayList();
    CommandeService cs = new CommandeService();
    PdfCommande pdfs = new PdfCommande();
    @FXML
    private TableColumn<Commande, Date> date_col;
    @FXML
    private TableColumn<Commande, Integer> user_col;
    @FXML
    private TableColumn<Commande, String> action_col;
    Commande commande = null;
    ObservableList<Commande> CommandeList = FXCollections.observableArrayList();
    @FXML
    private Button btnCustomers;
    @FXML
    private Label totalcmd;
    @FXML
    private Button btnOverview;
    @FXML
    private Button btnStats;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // init combobox
        ss.add("Par date");
        ss.add("Par user");
        ss.add("Par total");
        tableviewcommande.setEditable(true);
        tableviewcommande.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        combotri.setItems(ss);
        // to load data 
        loadData();
        // to search 
        recherche_avance();
        // clock 
        initClock();

    }
// refresh 

    public void refreshlist() {
        data.clear();
        data = FXCollections.observableArrayList(cs.affichageCommandes());

        user_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));

        etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        double count = cs.countCommande();
        totalcmd.setText(String.valueOf(count));
        tableviewcommande.setItems(data);
    }
    // navigatiion 

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
// tri 
    @FXML
    private void trilist(ActionEvent event) {
        if (combotri.getValue().equals("Par date")) {
            ObservableList<Commande> tri1 = FXCollections.observableArrayList();
            tri1 = FXCollections.observableArrayList(cs.sortByDate());
            tableviewcommande.setItems(tri1);

        } else if (combotri.getValue().equals("Par user")) {
            ObservableList<Commande> tri2 = FXCollections.observableArrayList();
            tri2 = FXCollections.observableArrayList(cs.sortByUser());
            tableviewcommande.setItems(tri2);
        } else if (combotri.getValue().equals("Par total")) {
            ObservableList<Commande> tri3 = FXCollections.observableArrayList();
            tri3 = FXCollections.observableArrayList(cs.sortByTotal());
            tableviewcommande.setItems(tri3);
        }
    }
// recherche
    @FXML
    public void recherche_avance() {
        System.out.println("*******************");

        FilteredList<Commande> filtereddata = new FilteredList<>(data, b -> true);
        System.out.println(recherchetf.getText());
        recherchetf.textProperty().addListener((observable, oldvalue, newValue) -> {
            filtereddata.setPredicate((Commande commande) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercasefilter = newValue.toLowerCase();
                if (commande.getEtat().toLowerCase().indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(commande.getTotal()).indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(commande.getUser_id()).indexOf(lowercasefilter) != -1) {
                    return true;
                } else if (String.valueOf(commande.getDate()).indexOf(lowercasefilter) != -1) {
                    return true;
                } else {
                    return false;
                }

            });

        });
        SortedList<Commande> sorteddata = new SortedList<>(filtereddata);
        sorteddata.comparatorProperty().bind(tableviewcommande.comparatorProperty());
        tableviewcommande.setItems(filtereddata);
    }
// time - horloge 
    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            currentTimeTF.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
// load data
    private void loadData() {
        data.clear();
        data = FXCollections.observableArrayList(cs.affichageCommandes());

        user_col.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        user_col.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        // update
        user_col.setOnEditCommit(new EventHandler<CellEditEvent<Commande, Integer>>() {
            @Override
            public void handle(CellEditEvent<Commande, Integer> event) {
                Commande person = event.getRowValue();
                person.setUser_id(event.getNewValue());
            }
        });
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        // update

        total_col.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        total_col.setOnEditCommit(new EventHandler<CellEditEvent<Commande, Double>>() {
            @Override
            public void handle(CellEditEvent<Commande, Double> event) {
                Commande person = event.getRowValue();
                person.setTotal(event.getNewValue());
                cs.modifierCommandetotal(event.getNewValue(), person.getId());

            }
        });
        etat_col.setCellValueFactory(new PropertyValueFactory<>("etat"));
        // update

        etat_col.setCellFactory(TextFieldTableCell.forTableColumn());
        etat_col.setOnEditCommit(new EventHandler<CellEditEvent<Commande, String>>() {
            @Override
            public void handle(CellEditEvent<Commande, String> event) {
                Commande person = event.getRowValue();
                person.setEtat(event.getNewValue());
                cs.modifierCommandeetat(event.getNewValue(), person.getId());

            }
        });
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
          double count = cs.countCommande();
        totalcmd.setText(String.valueOf(count));
        //  date_col.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        //add cell of button edit 
        Callback<TableColumn<Commande, String>, TableCell<Commande, String>> cellFoctory = (TableColumn<Commande, String> param) -> {
            // make cell containing buttons
            final TableCell<Commande, String> cell = new TableCell<Commande, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
// delete commande
                            try {
                                  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation Dialog");
                                alert.setContentText("Etes vous sure de supprimer cet element ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {

                                    commande = tableviewcommande.getSelectionModel().getSelectedItem();
                                CommandeService rs = new CommandeService();
                                rs.supprimerCommande((int) commande.getId());

                                refreshlist();
                                } else {

                                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                                    alert1.setTitle("Information Dialog");
                                    alert1.setHeaderText(null);
                                    alert1.setContentText("Veuillez sélectionner un element à supprimer.");

                                    alert1.showAndWait();
                                }
                              
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }

                        });

                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        action_col.setCellFactory(cellFoctory);
        tableviewcommande.setItems(CommandeList);

    }
// ignore this
    @FXML
    private void user_id_OnEditCommit(Event e) {
    }

    @FXML
    private void total_OnEditCommit(Event e) {
    }

    @FXML
    private void date_OnEditCommit(Event e) {
    }

    @FXML
    private void etat_OnEditCommit(Event e) {
    }

    @FXML
    private void pdf(MouseEvent event) throws FileNotFoundException, DocumentException {
    pdfs.liste_CommandePDF();
    }


}
