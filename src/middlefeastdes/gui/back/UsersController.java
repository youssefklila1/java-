package middlefeastdes.gui.back;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import middlefeastdes.entity.User;
import middlefeastdes.service.UserService;

public class UsersController {

    @FXML
    private Button addUserBtn;

    @FXML
    private AnchorPane productDisplayArea;

    @FXML
    private Button viewUsersBtn;
    
      @FXML
    private Button statsBtn;
      
      
    @FXML
    private Button getPDF;
      
      List<User> users = new ArrayList<>();
      
      UserService userService = new UserService();
      
          @FXML
    void PDF(ActionEvent event) throws FileNotFoundException, DocumentException, SQLException {
         String message = "Users List \n\n";
         String file_name = "src/Users.pdf";
           Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file_name));
        document.open();
        Paragraph para = new Paragraph(message);
        document.add(para);
        List<User> userList = userService.findAll();
        PdfPTable table = new PdfPTable(4);
        
        
        PdfPCell cl1 = new PdfPCell(new Phrase("User ID"));
        table.addCell(cl1);
        PdfPCell cl = new PdfPCell(new Phrase("Email"));
        table.addCell(cl);
        PdfPCell cl2 = new PdfPCell(new Phrase("Firstname"));
        table.addCell(cl2);
        PdfPCell cl3 = new PdfPCell(new Phrase("Lastname"));
        table.addCell(cl3);
        
          table.setHeaderRows(1);
        document.add(table);
        
        userList.forEach((t) -> {
            table.addCell(""+t.getId());
            table.addCell(""+t.getEmail());
            table.addCell(""+t.getFirstname());
            table.addCell(""+t.getLastname());
        });
          document.add(table);

        document.close();
        
    }
      
          @FXML
    void loadStats(ActionEvent event) throws SQLException {
        users = userService.findAll();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Number Of Users");
        xAxis.setLabel("Users");       
        yAxis.setLabel("Number");
        bc.setAnimated(true);
        
        
         XYChart.Series series1 = new XYChart.Series();
        series1.setName("Users");       
        series1.getData().add(new XYChart.Data("Users "+users.size(), users.size()));
       bc.getData().addAll(series1);
       
       Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
     
        final ScrollPane scroll = new ScrollPane();
         scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        Scene scene = new Scene(scroll);
        FlowPane flowPane = new FlowPane(bc);
        scroll.setContent(flowPane);
        
        stage.setWidth(800);
        stage.setHeight(600);
           
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void loadAddUser(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);

    }

    @FXML
    void loadViewUsers(ActionEvent event) throws IOException {
         Parent fxml = FXMLLoader.load(getClass().getResource("ShowAllUsers.fxml"));
        productDisplayArea.getChildren().removeAll();
        productDisplayArea.getChildren().add(fxml);

    }

}
