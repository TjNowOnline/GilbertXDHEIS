package org.example.gilbertxdheis.application;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

        public static final String ACCOUNT_SID = "ACc4fe8ee3a65d89f5953265d6352c738a";
        public static final String AUTH_TOKEN = "ac51ee972ae7ad64da2b81d899abafe3";


        public void sendSms() {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber("+4520994340"),
                    new PhoneNumber("14632827035"),
                    "Hej fra Java via Twilio! 🚀"
            ).create();

            System.out.println("SMS sendt! SID: " + message.getSid());
        }
    }


