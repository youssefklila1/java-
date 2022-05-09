package middlefeastdes.gui.front.tutorials;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.nayuki.qrcodegen.QrCode;
import middlefeastdes.entity.Tutorial;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.scene.control.Alert;
import javax.imageio.ImageIO;
import middlefeastdes.entity.User;
import middlefeastdes.gui.front.UserSession;
import middlefeastdes.utils.Mail;

public class FindTutorialByIdController implements Initializable {

    @FXML
    private Button btnCart;

    @FXML
    private ImageView imgDisplay;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblTitle;

    private Tutorial tutorial;
    Mail mailer = new Mail();
    
    User user;
    UserSession userSession = UserSession.getInstace();
    
    public static final String ACCOUNT_SID = "AC75f5a79b957502babd32f755a5bb51de";
    public static final String AUTH_TOKEN = "9673f53fbbf81b0dea3f6693f479fc59";

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Image image = new Image("http://127.0.0.1/uploads/" + tutorial.getImage());
            imgDisplay.setImage(image);
            lblDescription.setText(tutorial.getDescription());
            lblTitle.setText(tutorial.getTitre());
            lblPrice.setText(tutorial.getPrix() > 0 ? String.valueOf(tutorial.getPrix()) + " TND" : "Free");
            lblCategory.setText(tutorial.getCategory());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            lblDate.setText(simpleDateFormat.format(tutorial.getDateTuto()));
            if (!(tutorial.getPrix() > 0)) {
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
        user = UserSession.getInstace().getConnectedUser();
        QrCode qr0 = QrCode.encodeText(tutorial.print()+" || "+user.print(), QrCode.Ecc.MEDIUM);
        BufferedImage img = toImage(qr0, 4, 10, 0xFFFFFF, 0x000000);  // See QrCodeGeneratorDemo
        ImageIO.write(img, "png", new File("qr-code.png"));
        mailer.sendTutoQrEmail(user.getEmail(), "Thank you for your Purchase \n You can use the attached Qr Code to verify Identify");
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

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }
}
