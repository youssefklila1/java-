/** *****************************************************************************
 * Controller class and logic implementation for payment.fxml
 ***************************************************************************** */
package GUI;

import com.jfoenix.controls.*;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class PaymentController implements Initializable {

    @FXML
    private AnchorPane mainpane, insidepane, methodpane;
    @FXML
    private ImageView cardimage, cashimage;


    public JFXComboBox expmonthbox, expyearbox;
    private JFXTextField firstnamefield;

    private Label errorlabel;
    public static String scene = "method";
    public static boolean reversemethod = false;
    private String moveto = "";
    private boolean hided = false;
    public boolean moneytaken = false, printing = false, handledpayment = false;
    @FXML
    private VBox pnItems;
    @FXML
    private javafx.scene.control.Button btnOverview;
    @FXML
    private javafx.scene.control.Button btnCustomers;
    @FXML
    private javafx.scene.control.Button btnMenus;
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

    }

    @FXML
    private void cardpayment(MouseEvent event) {
        this.handledpayment=true ;     
        AnchorPane panee;
        try {
            panee = FXMLLoader.load(getClass().getResource("Card-payment.fxml"));
            mainpane.getChildren().setAll(panee);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void cashpayment(MouseEvent event) {
                this.moneytaken=true ; 
                     AnchorPane panee;
        try {
            panee = FXMLLoader.load(getClass().getResource("Cash-payment.fxml"));
            mainpane.getChildren().setAll(panee);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnPanier) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Panier.fxml"));
            mainpane.getChildren().setAll(panee);
        }
        if (actionEvent.getSource() == btnCustomers) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Commande.fxml"));
            mainpane.getChildren().setAll(panee);
        }
        if (actionEvent.getSource() == btnStats) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("Statistique.fxml"));
            mainpane.getChildren().setAll(panee);
        }
           if (actionEvent.getSource() == btnMenus) {
            AnchorPane panee = FXMLLoader.load(getClass().getResource("CommandeFront.fxml"));
            mainpane.getChildren().setAll(panee);
        }
    }
    



   

}
