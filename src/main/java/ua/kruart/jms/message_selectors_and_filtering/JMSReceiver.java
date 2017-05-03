package ua.kruart.jms.message_selectors_and_filtering;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 02.05.2017.
 */
public class JMSReceiver implements MessageListener {

    public JMSReceiver() {
        try {
            Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue("EM_TRADE_FILTER.Q");
            MessageConsumer receiver = session.createConsumer(queue, "Stage = 'open'");

            receiver.setMessageListener(this);
            System.out.println("Waiting for message...");
            System.out.println("filter: " + receiver.getMessageSelector());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage msg = (TextMessage) message;
            System.out.println(msg.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(JMSReceiver::new).start();
    }
}
