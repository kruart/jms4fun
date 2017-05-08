package ua.kruart.jms.advanced_features.embedded.bootstrap_mode;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSReceiver {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61888").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
        Queue queue = session.createQueue("EM_EMBED_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);
        System.out.println("Waiting for messages...");
        TextMessage msg = (TextMessage) receiver.receive();
        System.out.println(msg.getText());
        connection.close();
    }
}
