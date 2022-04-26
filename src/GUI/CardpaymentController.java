/** *****************************************************************************
 * Controller class and logic implementation for payment.fxml
 ***************************************************************************** */
package GUI;

import Entities.Commande;
import Services.CommandeService;
import Services.PanierService;
import Tools.Smsapi;
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
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class CardpaymentController implements Initializable {

    //Inisilisation of javafx objects and variables used.
    @FXML
    private JFXButton placeorderbtn, backcardbtn;

    public JFXComboBox expmonthbox, expyearbox;
    @FXML
    private JFXTextField firstnamefield, lastnamefield, emailfield, cardfield,
            cvvfield;

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

    /**
     * Initialise method that gets run whenever the payment.fxml is loaded.
     * Needed in order to initialise and set up all the logic for payment.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ss.add("01");
        ss.add("02");
        year.add("2022");
        year.add("2024");
        expmonthbox.setItems(ss);
        expyearbox.setItems(year);
        placeorderbtn.setOnAction((ActionEvent event) -> {
            if (controleDeSaisi()) {

                try {
                       Stripe.apiKey = "sk_test_51KsbA3GApky3j2RcV5POveAJEIJcGoQPEMbQ322bklIREolZ26mq8cyFrlw9RzaZGyqMddbK9ayOSa3bx0JdO7ub00A0ppr3kL";
                    
                  //  Stripe.apiKey = "pk_test_51KsbA3GApky3j2RcKkYHEbP5NdqQ9lQFjIcjSvjwTD6QW7FwRWfIoWV5xoI0zlEMtMXHnTlWp2fZ8mTXgtKi2FiW00NVwhvAWY";
                    // Stripe.apiKey = "sk_test_51KsAbAK82nWAyMQiitThm4m0K92jJRsV25YnqJZjlO9y7ejFp4lrVPVnucznqZ1F0q4oNxN6AFv5rzKrH0Ab96wD00px5z8MLm";
                    System.out.println("Chargement paiement...");
                    CustomerRetrieveParams params = CustomerRetrieveParams.builder()
                            .addExpand("sources").build();
                    Customer stripeCustomer = Customer.retrieve("cus_LZl3fpIQr4P3Wg", params, null);
                    //Customer stripeCustomer = Customer.retrieve("cus_LZJgnsBUiF8npn", params, null);
                    System.out.println("Paiement effectué");

                    Map<String, Object> cardParam = new HashMap<String, Object>();
                    cardParam.put("number", cardfield.getText());
                    cardParam.put("exp_month", expmonthbox.getValue().toString());
                    cardParam.put("exp_year", expyearbox.getValue().toString());
                    cardParam.put("cvc", cvvfield.getText());

                    Map<String, Object> tokenParams = new HashMap<String, Object>();
                    tokenParams.put("card", cardParam);

                    Token token = Token.create(tokenParams);

                    stripeCustomer.getSources();

                    Map<String, Object> chargeParams = new HashMap<String, Object>();
                    double somme = ps.sommePanier(1);
                    chargeParams.put("amount", Math.round(somme * 100));
                    chargeParams.put("currency", "usd");
                    chargeParams.put("customer", stripeCustomer.getId());

                    Charge.create(chargeParams);
                    System.out.print("transaction réussite"); // ***

                    showAlert(Alert.AlertType.CONFIRMATION, "Données Valide", "Success", "Payment avec succes!");
                    Date date = new Date(System.currentTimeMillis());
                    Commande c = new Commande(1, somme, date, " paid");
                    cs.ajouterCommande(c);
                    ps.supprimerPanierbyuser(1);
                    Smsapi.sendSMS("Commande effectuée avec succes");
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
            } else {
                showAlert(Alert.AlertType.ERROR, "Données inValide", "error", "Payment not done!");
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
                    Logger.getLogger(CardpaymentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    private boolean controleDeSaisi() {

        if (cardfield.getText().isEmpty() || cvvfield.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("[0-9]*", expmonthbox.getValue().toString())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le Mois ! ");
                expmonthbox.requestFocus();
                return false;
            }
            if (!Pattern.matches("[0-9]*", expyearbox.getValue().toString())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez l'année ! ");
                expyearbox.requestFocus();
                return false;
            }
            if (!Pattern.matches("[0-9]*", cvvfield.getText())) {
                showAlert(Alert.AlertType.ERROR, "Données ", "Verifier les données", "Vérifiez le cvc ! ");
                cvvfield.requestFocus();
                cvvfield.selectEnd();
                return false;
            }

        }
        return true;

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

    @FXML
    private void handleButtons(ActionEvent event) {
    }

}
