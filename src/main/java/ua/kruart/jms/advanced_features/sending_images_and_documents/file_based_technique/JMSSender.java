package ua.kruart.jms.advanced_features.sending_images_and_documents.file_based_technique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;
import java.util.UUID;

/**
 * Created by kruart on 08.05.2017.
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
        String filename = copyFileToTemp();
        TextMessage msg = session.createTextMessage(filename);
        sender.send(msg);
        System.out.println("Message sent...");

        connection.close();
        System.exit(0);
    }

    public String copyFileToTemp() throws Exception {
        File inFile = new File(sourceFile);
        String outFile = tempDir + UUID.randomUUID();
        try(InputStream is = new FileInputStream(inFile)) {
            try(OutputStream os = new FileOutputStream(outFile)) {
                byte[] bytes = new byte[(int) inFile.length()];
                is.read(bytes);
                os.write(bytes);
                return outFile;
            }
        }
    }
}
