package ua.kruart.jms.embedded.bootstrap_mode;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSSender {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61888").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
        Queue queue = session.createQueue("EM_EMBED_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);

        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");
        sender.send(msg);
        System.out.println("Message sent ...");

        connection.close();
    }
}
