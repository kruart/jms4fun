package ua.kruart.jms.advanced_features.embedded.application_mode_vm;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSVmReceiver implements MessageListener {
    private int id;

    public JMSVmReceiver(int id) {
        try {
            this.id = id;

            Connection connection = new ActiveMQConnectionFactory("vm://embedded1").createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
            Queue queue = session.createQueue("EM_EMBED_TRADE.Q");
            MessageConsumer receiver = session.createConsumer(queue);
            receiver.setMessageListener(this);
            System.out.println("Receiver (" + id  + "): Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            Thread.sleep(1000);
            System.out.println("Trade Receiver (" + id + "): " + msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
