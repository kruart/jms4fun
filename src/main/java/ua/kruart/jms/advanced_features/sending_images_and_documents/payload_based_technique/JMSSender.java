package ua.kruart.jms.advanced_features.sending_images_and_documents.payload_based_technique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by kruart on 09.05.2017.
 */
public class JMSSender {

    private static String sourceFile = "D:\\projects\\java\\jms\\jms_4fun\\images\\juve_newLogo.jpg";
    private static String tempDir = "D:\\projects\\java\\jms\\jms_4fun\\images\\tempImageDir\\";

    private Connection connection;
    private Session session;
    private MessageProducer sender;
    private String uuid;

    public JMSSender() throws Exception {
        connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("EM_IMAGE.Q");
        sender = session.createProducer(queue);
    }

    public static void main(String[] args) throws Exception {
        new JMSSender().sendMessage();
    }


    public void sendMessage() throws Exception {
    	byte[] fileContents = readFileFromSource();
    	BytesMessage msg = session.createBytesMessage();
    	msg.writeBytes(fileContents);
    	sender.send(msg);
		System.out.println("Message Sent");

        connection.close();
        System.exit(0);
    }

    public byte[] readFileFromSource() throws Exception {
        File file = new File(sourceFile);
        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            is.read(bytes);
            return bytes;
        }
    }
}

