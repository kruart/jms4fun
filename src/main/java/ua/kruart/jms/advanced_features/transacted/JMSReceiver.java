package ua.kruart.jms.advanced_features.transacted;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 07.05.2017.
 */
public class JMSReceiver implements MessageListener {

    public JMSReceiver() {
        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
            Queue queue = session.createQueue("EM_TRADE.Q");
            MessageConsumer receiver = session.createConsumer(queue);

            //register message listener
            receiver.setMessageListener(this);
            System.out.println("waiting for messages");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println("JMSReceiver: " + msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(JMSReceiver::new).start();
    }
}
