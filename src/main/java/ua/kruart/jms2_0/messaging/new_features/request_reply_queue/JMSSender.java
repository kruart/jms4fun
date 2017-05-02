package ua.kruart.jms2_0.messaging.new_features.request_reply_queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 01.05.2017.
 */
public class JMSSender {

    public static void main(String[] args) throws JMSException {
        //First variant
//        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");
//        Queue queueResp = session.createQueue("EM_TRADE_RESP.Q");
//        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");
//        msg.setJMSReplyTo(queueResp);   //I would like the consumer to reply to me on this queue
//        MessageProducer sender = session.createProducer(queueReq);
//        sender.send(msg);
//        System.out.println("Message sent");
//
//        String filter = "JMSCorrelationID = \'" + msg.getJMSMessageID() + "\'";
//        MessageConsumer receiver = session.createConsumer(queueResp, filter);
//        TextMessage msgResp = (TextMessage) receiver.receive(); //here i'm listening, waiting and blocking for a response
//        System.out.println("confirmation = " + msgResp.getText());
//        connection.close();

        //Second variant
        QueueConnection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createQueueConnection();
        connection.start();
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queueReq = session.createQueue("EM_TRADE_REQ.Q");

        TextMessage msg = session.createTextMessage("BUY AAPL 1000 SHARES");

        QueueRequestor requestor = new QueueRequestor(session, queueReq);
        TextMessage msgresp = (TextMessage) requestor.request(msg);

        System.out.println("conf = " + msgresp.getText());


        connection.close();
    }
}
