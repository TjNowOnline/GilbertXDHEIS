package org.example.gilbertxdheis.application;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

        public static final String ACCOUNT_SID = "xx";
        public static final String AUTH_TOKEN = "x";


        public void sendSms() {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber("+45xxxxxx"),
                    new PhoneNumber("xxxxxxx"),
                    "Hej fra Java via Twilio! 🚀"
            ).create();

            System.out.println("SMS sendt! SID: " + message.getSid());
        }
    }


