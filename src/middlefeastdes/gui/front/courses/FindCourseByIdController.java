package middlefeastdes.gui.front.courses;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.nayuki.qrcodegen.QrCode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import middlefeastdes.entity.Course;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import middlefeastdes.entity.User;
import middlefeastdes.gui.front.UserSession;
import static middlefeastdes.gui.front.tutorials.FindTutorialByIdController.ACCOUNT_SID;
import static middlefeastdes.gui.front.tutorials.FindTutorialByIdController.AUTH_TOKEN;
import middlefeastdes.utils.Mail;

public class FindCourseByIdController implements Initializable {


    @FXML
    private Button btnCart;

    @FXML
    private Label lblDateDebut;

    @FXML
    private Label lblDateFin;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblMode;

    @FXML
    private Label lblPrice;

    private Course course;
    
     Mail mailer = new Mail();
    
    User user;
    
    UserSession userSession = UserSession.getInstace();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            lblDescription.setText(course.getDescription());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDateDebut.setText(simpleDateFormat.format(course.getDateDebut()));
            lblDateFin.setText(simpleDateFormat.format(course.getDateFin()));
            lblPrice.setText(course.getPrice() > 0 ? String.valueOf(course.getPrice())+ " TND" : "Free");
            lblDuration.setText(Integer.parseInt(course.getDuree()) < 2 ? course.getDuree() + " Day" : course.getDuree() + " Days");
            lblMode.setText(course.getMode());
            if(!(course.getPrice() > 0)){
                btnCart.setText("Get now!");
            }
        });
    }

    @FXML
    void addToCart(ActionEvent event) throws IOException {
        
        
        
        
        if(userSession.getConnectedUser()==null)
        {
            Alert a = new Alert(Alert.AlertType.NONE);
             a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Please Login First !");
                a.show();
        }else
        {
             QrCode qr0 = QrCode.encodeText(course.print()+" || "+userSession.getConnectedUser().print(), QrCode.Ecc.MEDIUM);
        BufferedImage img = toImage(qr0, 4, 10, 0xFFFFFF, 0x000000);  // See QrCodeGeneratorDemo
        ImageIO.write(img, "png", new File("qr-code.png"));
        mailer.sendCourseQrEmail(userSession.getConnectedUser().getEmail(), "Thank you for your Purchase \n You can use the attached Qr Code to verify Identify");
           Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+21650804985"),new PhoneNumber("+19894613306"), "Thank you for your Purchase ! Check your email where you can find Qr Code to verify Identify").create();

        System.out.println(message.getSid());
        }
        
       

    }
    
      private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
