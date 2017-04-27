package ua.kruart.jms.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 27.04.2017.
 */
public class JMSAsyncReceiver implements MessageListener {

    public JMSAsyncReceiver() {
        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
            System.out.println("JMSAsyncReceiver: " + msg.getText() + " TraderName: " + msg.getStringProperty("TraderName"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(JMSAsyncReceiver::new).start();
    }
}

