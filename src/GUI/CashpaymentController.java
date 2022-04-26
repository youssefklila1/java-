/** *****************************************************************************
 * Controller class and logic implementation for payment.fxml
 ***************************************************************************** */
package GUI;

import Entities.Commande;
import Services.CommandeService;
import Services.PanierService;
import com.jfoenix.controls.*;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.CustomerRetrieveParams;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextFormatter;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerRetrieveParams;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class CashpaymentController implements Initializable {

    //Inisilisation of javafx objects and variables used.
    @FXML
    private JFXButton placeorderbtn, backcardbtn;

    public JFXComboBox expmonthbox, expyearbox;
    @FXML
    private JFXTextField firstnamefield;
    @FXML
    private JFXTextField lastnamefield;
    @FXML
    private JFXTextField emailfield;
    private JFXTextField cardfield;

    private Label errorlabel;
    public static String scene = "method";
    public static boolean reversemethod = false;
    private String moveto = "";
    private boolean hided = false;
    private boolean moneytaken = false, printing = false, handledpayment = false;
    @FXML
    private VBox pnItems;
    @FXML
    private javafx.scene.control.Button btnOverview;
    @FXML
    private javafx.scene.control.Button btnCustomers;
    @FXML
    private javafx.scene.control.Button btnMenus;
    ObservableList<String> ss = FXCollections.observableArrayList();
    ObservableList<String> year = FXCollections.observableArrayList();
    PanierService ps = new PanierService();
    CommandeService cs = new CommandeService();
    @FXML
    private AnchorPane DashboardUtilis;
    @FXML
    private javafx.scene.control.Button btnPanier;
    @FXML
    private javafx.scene.control.Button btnStats;
    @FXML
    private JFXTextField Location;

    /**
     * Initialise method that gets run whenever the payment.fxml is loaded.
     * Needed in order to initialise and set up all the logic for payment.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        placeorderbtn.setOnAction((ActionEvent event) -> {

            try {

                double somme = ps.sommePanier(1);

                showAlert(Alert.AlertType.CONFIRMATION, "Données Valide", "Success", "Payment avec succes!");
                Date date = new Date(System.currentTimeMillis());
                Commande c = new Commande(1, somme, date, "not paid");
                cs.ajouterCommande(c);
                ps.supprimerPanierbyuser(1);

                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setTitle("Command done");
                tray.setMessage("You successufuly made your first command ");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(1000));
                AnchorPane panee;
                try {
                    panee = FXMLLoader.load(getClass().getResource("Panier.fxml"));
                    DashboardUtilis.getChildren().setAll(panee);
                } catch (IOException ex) {
                    Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  Smsapi.sendSMS("hello");

            } catch (Exception ex) {
            }

        });

        backcardbtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                AnchorPane panee;
                try {
                    panee = FXMLLoader.load(getClass().getResource("Panier.fxml"));
                    DashboardUtilis.getChildren().setAll(panee);
                } catch (IOException ex) {
                    Logger.getLogger(CashpaymentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    public static void showAlert(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

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

    private void Localiser(ActionEvent event) throws Exception {
        //try {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            Parent root = loader.load();
            MapController cr = loader.getController();*/
        WebViewCaptureMap w = new WebViewCaptureMap();
        Stage stage = new Stage();
        w.start(stage);
        /*stage.setScene(new Scene(root));
            MainGUICategorie m = new MainGUICategorie();
            m.Map(stage);
            stage.show();*/

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                MapController mp = new MapController();
                Location.setText(mp.getMap_value());
                if (!"".equals(Location.getText())) {
                    //  btn_localiser.setDisable(true);
                    //System.out.println("done");
                }
            }
        });

        /*} catch (IOException ex) {
            Logger.getLogger(Version_3Controller.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @FXML
    private void Localiser(MouseEvent event) {
        //try {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
            Parent root = loader.load();
            MapController cr = loader.getController();*/
        WebViewCaptureMap w = new WebViewCaptureMap();
        Stage stage = new Stage();
        try {
            w.start(stage);
            /*stage.setScene(new Scene(root));
            MainGUICategorie m = new MainGUICategorie();
            m.Map(stage);
            stage.show();*/
        } catch (Exception ex) {
            Logger.getLogger(CashpaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                MapController mp = new MapController();
                Location.setText(mp.getMap_value());
                if (!"".equals(Location.getText())) {
                    //  btn_localiser.setDisable(true);
                    //System.out.println("done");
                }
            }
        });
    }

    @FXML
    private void handleButtons(ActionEvent event) {
    }

}
