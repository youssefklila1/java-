/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import com.twilio.Twilio;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
/**
 * @author MSI
 */
public class Smsapi {

    public static final String ACCOUNT_SID = "ACe9aecb69f85e0309a74831bf2b327ff4";
    public static final String AUTH_TOKEN = "9846d68772f813e37dee3572067c0e89";

    public static void sendSMS(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+21628597602"),new PhoneNumber("++17152652638"), msg).create();

        System.out.println(message.getSid());

    } 
}
