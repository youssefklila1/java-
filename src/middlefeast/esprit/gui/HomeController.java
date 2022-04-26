package middlefeast.esprit.gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;
import middlefeast.esprit.entities.Discussion;
import middlefeast.esprit.services.DiscussionService;

public class HomeController implements Initializable {
    private Parent fxml;
    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnForum;

    @FXML
    private Button btnSignout;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        

    }

    @FXML
    void btnForumAction(){
        //System.out.println("change");
        try {
            fxml= FXMLLoader.load(getClass().getResource("Forum.fxml"));
            root.getChildren().removeAll();
            root.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void changeView(int id){
         try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Discussion.fxml"));
            Parent root2= fxmlLoader.load();
            
            
            DiscussionController controller = fxmlLoader.getController();
            controller.setThematiqueId(id);
            controller.loadData();
            root.getChildren().removeAll();
            root.getChildren().setAll(root2);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #1620A1");
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #53639F");
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.setStyle("-fx-background-color : #464F67");
            pnlOrders.toFront();
        }
    }
}
