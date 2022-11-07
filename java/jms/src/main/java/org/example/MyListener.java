package org.example;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener {
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println(((TextMessage) message).getText());
            }
        } catch (Exception e) {
        }
    }
}

