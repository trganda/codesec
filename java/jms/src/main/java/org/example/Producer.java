package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Producer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection("trganda", "245680");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("orders");
        MessageProducer producer = session.createProducer(queue);

        connection.start();

        TextMessage message = session.createTextMessage();
        message.setText("Hello JMS!");

        producer.send(message);

        connection.close();
    }
}
