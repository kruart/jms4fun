package ua.kruart.jms.messaging;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSReceiver {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);

        //get message
        TextMessage msg = (TextMessage) receiver.receive();
        System.out.println("Receiver: " + msg.getText());
        connection.close();

    }
}
