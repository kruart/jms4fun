package ua.kruart.jms.advanced_features.embedded.application_mode_vm;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSVmSender {

    private Session session;
    private MessageProducer sender;

    public JMSVmSender() {
        try {
            Connection connection = new ActiveMQConnectionFactory("vm://embedded1").createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);//false - in no transaction; true - in transaction
            Queue queue = session.createQueue("EM_EMBED_TRADE.Q");
            sender = session.createProducer(queue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String trade) {
        try {
            TextMessage msg = session.createTextMessage(trade);
            sender.send(msg);
            System.out.println("Trade sent ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
