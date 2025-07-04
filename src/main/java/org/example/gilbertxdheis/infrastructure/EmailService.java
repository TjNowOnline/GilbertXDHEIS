package org.example.gilbertxdheis.infrastructure;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService {

    public static class MailSystem {
        public static void sendmail(String recepient, String subject, String messageBody) throws Exception {

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            String myAccountEmail = "tino03543@gmail.com";
            String myPassword = "xxxxxxxxxxxxx";

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, myPassword);
                }
            });
            Message message = prepareMessage(session, myAccountEmail, recepient, subject,messageBody);
            Transport.send(message);


        }

        private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String subject, String messageBody) {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(myAccountEmail));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
                message.setSubject(subject);
                message.setText(messageBody);
                return message;
            } catch (Exception e) {
                Logger.getLogger(MailSystem.class.getName()).log(Level.SEVERE, null, e);
            }
            return null;
        }
    }
}
