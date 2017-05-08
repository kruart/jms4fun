package ua.kruart.jms.basics.new_features.request_reply_queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 01.05.2017.
 */
public class JMSReceiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        //First variant
//        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("EM_TRADE_REQ.Q");
//        MessageConsumer receiver = session.createConsumer(queue);
//        TextMessage msg = (TextMessage) receiver.receive();
//
//        System.out.println("processing trade: " + msg.getText());
//        Thread.sleep(4000);
//        String confirmation = "EQ-12345";
//        System.out.println("trade confirmation: " + confirmation);
//
//        TextMessage outmsg = session.createTextMessage(confirmation);
//        //setJMSCorrelationID() - //contains a string value used during request/reply processing for the producer to locate message response.
//        outmsg.setJMSCorrelationID(msg.getJMSMessageID());
//        MessageProducer sender = session.createProducer((Queue) msg.getJMSReplyTo());
//        sender.send(outmsg);
//
//        connection.close();

        //Second variant

        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("EM_TRADE_REQ.Q");
        MessageConsumer receiver = session.createConsumer(queue);
        TextMessage msg = (TextMessage) receiver.receive();

        System.out.println("processing trade: " + msg.getText());
        Thread.sleep(4000);
        String confirmation = "EQ-12345";
        System.out.println("trade confirmation: " + confirmation);

        TextMessage outmsg = session.createTextMessage(confirmation);
        MessageProducer sender = session.createProducer(msg.getJMSReplyTo());
        sender.send(outmsg);

        connection.close();
    }
}
