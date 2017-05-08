package ua.kruart.jms.advanced_features.connection_metadata;

import com.sun.messaging.ConnectionFactory;

import javax.jms.ConnectionMetaData;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import java.util.Enumeration;

/**
 * Created by kruart on 08.05.2017.
 */
public class JMS2MetaData {

    public static void main(String[] args) {
        ConnectionFactory cf = new ConnectionFactory();
        try(JMSContext jmsContext = cf.createContext()) {
            ConnectionMetaData metadata = jmsContext.getMetaData();
            System.out.println(" ");
            System.out.println("JMS Version: " + metadata.getJMSVersion());
            System.out.println("JMS Provider: " + metadata.getJMSProviderName());
            System.out.println("JMS Provider version: " + metadata.getProviderVersion());
            System.out.println("JMSX Properties Supported: ");

            Enumeration<?> jmsxPropertyNames = metadata.getJMSXPropertyNames();
            while (jmsxPropertyNames.hasMoreElements()) {
                System.out.println(" " + jmsxPropertyNames.nextElement());
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
