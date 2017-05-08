package ua.kruart.jms.basics.message_selectors_and_filtering;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMSSender {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue("EM_TRADE_FILTER.Q");
        MessageProducer publisher = session.createProducer(queue);

        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");
        msg.setStringProperty("Stage", "open");

        publisher.send(msg);
        System.out.println("Message sent");
        connection.close();
    }
}
