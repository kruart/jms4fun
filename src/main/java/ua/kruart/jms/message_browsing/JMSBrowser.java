package ua.kruart.jms.message_browsing;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Enumeration;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSBrowser {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
        Queue queue = session.createQueue("EM_TRADE.Q");

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration<?> enumeration = browser.getEnumeration();

        int msgCount = 0;
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement();
            msgCount++;
        }

        System.out.println("There are " + msgCount + " messages in the queue");

        browser.close();
        connection.close();
    }
}
