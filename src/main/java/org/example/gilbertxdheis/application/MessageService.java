package org.example.gilbertxdheis.application;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.example.gilbertxdheis.infrastucture.EmailService;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public void messageService(String recepient, String subject, String message) throws
            Exception {
        try {
           EmailService.MailSystem.sendmail(recepient, subject, message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}






