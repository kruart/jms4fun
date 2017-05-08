package ua.kruart.jms.basics.acknowledge_mode;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 03.05.2017.
 */
public class JMSReceiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();

        //AUTO_ACKNOWLEDGE - automatically marks the message as delivered when received by the consumer. It's not going to wait till the end. It marks automatically.
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction

        //CLIENT_ACKNOWLEDGE - marks the message as delivered only when manually acknowledged by the consumer.
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE); //false - in no transaction
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageConsumer receiver = session.createConsumer(queue);
        TextMessage msg = (TextMessage) receiver.receive();

        System.out.println("Message Received, processing...");
        Thread.sleep(6000); //simulate a six seconds delay
        System.out.println("Message processed: " + msg.getText());
        msg.acknowledge();  //acknowledge the provider
        connection.close();
    }
}
