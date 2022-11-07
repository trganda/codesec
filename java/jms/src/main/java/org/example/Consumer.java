package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {

    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = factory.createConnection("trganda", "245680");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("orders");

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MyListener());

        connection.start();
        connection.close();
        /*

         */
    }
}
