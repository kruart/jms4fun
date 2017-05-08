package ua.kruart.jms.transacted;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by kruart on 07.05.2017.
 */
public class JMSSender {

    public static void main(String[] args) throws JMSException, InterruptedException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
        Queue queue = session.createQueue("EM_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);

        TextMessage msg1 = session.createTextMessage("BUY AAPL 1000 SHARES");
        sender.send(msg1);
        System.out.println("Message 1 sent ...");

        Thread.sleep(3000);

        TextMessage msg2 = session.createTextMessage("BUY IBM 2000 SHARES");
        sender.send(msg2);
        System.out.println("Message 2 sent ...");

        session.commit();
        connection.close();
    }
}
