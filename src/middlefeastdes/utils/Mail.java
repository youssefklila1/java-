/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mail {

    public String sendEmail(String toEmailAddress, String emailMessage) {

        String userName = "middlefeastesprit@gmail.com";
        String password = "azertyqwerty123!";
        String emailSubject = "User";
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            Session session = Session.getDefaultInstance(props, null);
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            String[] recipientList = toEmailAddress.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject(emailSubject);
            message.setContent(emailMessage, "text/html");
            // Send smtp message
            Transport tr = session.getTransport("smtp");
            tr.connect("smtp.gmail.com", 587, userName, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            System.out.println("Email Sent Successfully to : " + recipientAddress.toString());
            return emailMessage;
        } catch (MessagingException e) {
            return "Error in method sendEmailNotification: " + e;
        }
    }

    public String sendResetEmail(String toEmailAddress, String emailMessage) {

        String userName = "middlefeastesprit@gmail.com";
        String password = "azertyqwerty123!";
        String emailSubject = "Password Reset";
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            Session session = Session.getDefaultInstance(props, null);
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            String[] recipientList = toEmailAddress.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject(emailSubject);
            message.setContent(emailMessage, "text/html");
            // Send smtp message
            Transport tr = session.getTransport("smtp");
            tr.connect("smtp.gmail.com", 587, userName, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            System.out.println("Email Sent Successfully to : " + recipientAddress.toString());
            return emailMessage;
        } catch (MessagingException e) {
            return "Error in method sendEmailNotification: " + e;
        }
    }

    public String sendTutoQrEmail(String toEmailAddress, String emailMessage) throws IOException {

        String userName = "middlefeastesprit@gmail.com";
        String password = "azertyqwerty123!";
        String emailSubject = "Tutorial";
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            Session session = Session.getDefaultInstance(props, null);
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            String[] recipientList = toEmailAddress.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject(emailSubject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(emailMessage);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File("qr-code.png"));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send smtp message
            Transport tr = session.getTransport("smtp");
            tr.connect("smtp.gmail.com", 587, userName, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            System.out.println("Email Sent Successfully to : " + recipientAddress.toString());
            return emailMessage;
        } catch (MessagingException e) {
            return "Error in method sendEmailNotification: " + e;
        }
    }
    
    public String sendCourseQrEmail(String toEmailAddress, String emailMessage) throws IOException {

        String userName = "middlefeastesprit@gmail.com";
        String password = "azertyqwerty123!";
        String emailSubject = "Course";
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            Session session = Session.getDefaultInstance(props, null);
            // Create email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            String[] recipientList = toEmailAddress.split(",");
            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
            int counter = 0;
            for (String recipient : recipientList) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddress);
            message.setSubject(emailSubject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(emailMessage);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File("qr-code.png"));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send smtp message
            Transport tr = session.getTransport("smtp");
            tr.connect("smtp.gmail.com", 587, userName, password);
            message.saveChanges();
            tr.sendMessage(message, message.getAllRecipients());
            tr.close();
            System.out.println("Email Sent Successfully to : " + recipientAddress.toString());
            return emailMessage;
        } catch (MessagingException e) {
            return "Error in method sendEmailNotification: " + e;
        }
    }

}
