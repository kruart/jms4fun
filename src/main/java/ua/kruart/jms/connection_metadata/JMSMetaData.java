package ua.kruart.jms.connection_metadata;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import java.util.Enumeration;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMSMetaData {

    public static void main(String[] args) throws JMSException {
        Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        ConnectionMetaData metadata = connection.getMetaData();
        System.out.println(" ");
        System.out.println("JMS Version: " + metadata.getJMSVersion());
        System.out.println("JMS Provider: " + metadata.getJMSProviderName());
        System.out.println("JMS Provider version: " + metadata.getProviderVersion());
        System.out.println("JMSX Properties Supported: ");

        Enumeration<?> jmsxPropertyNames = metadata.getJMSXPropertyNames();
        while (jmsxPropertyNames.hasMoreElements()) {
            System.out.println(" " + jmsxPropertyNames.nextElement());
        }

        connection.close();
    }
}
