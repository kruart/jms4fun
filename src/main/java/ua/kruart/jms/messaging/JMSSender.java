package ua.kruart.jms.messaging;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by kruart on 27.04.2017.
 */
public class JMSSender {

    public static void main(String[] args) throws JMSException, NamingException {
//        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        Connection connection = cf.createConnection();
//        connection.start();
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        Queue queue = session.createQueue("EM_TRADE.Q");

        Context ctx = new InitialContext();
        Connection connection = ((ConnectionFactory)ctx.lookup("ConnectionFactory")).createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = (Queue) ctx.lookup("EM_TRADE.Q");
        MessageProducer sender = session.createProducer(queue);

        //send message
        TextMessage msg = session.createTextMessage("SELL AAPL 9999 SHAPES");
        msg.setStringProperty("TraderName", "Arthur");
        sender.send(msg);
        System.out.println("Sender: message sent");
        connection.close();
    }
}

