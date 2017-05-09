package ua.kruart.jms.advanced_features.sending_images_and_documents.payload_based_technique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.*;

/**
 * Created by kruart on 09.05.2017.
 */
public class JMSReceiver implements MessageListener {

    private static String targetFile = "D:\\projects\\java\\jms\\jms_4fun\\images\\targetDir\\juve.jpg";
    private OutputStream os;

    public static void main(String[] args) {
        new Thread(JMSReceiver::new).start();
    }

    public JMSReceiver() {
        try {
            Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //false - in no transaction; true - in transaction
            Queue queue = session.createQueue("EM_IMAGE.Q");
            MessageConsumer receiver = session.createConsumer(queue);
            receiver.setMessageListener(this);
//            os = new FileOutputStream(targetFile);
            System.out.println("Waiting for messages...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            BytesMessage msg = (BytesMessage) message;
			System.out.println("Message Received");
			byte[] bytes = new byte[new Long(msg.getBodyLength()).intValue()];
			msg.readBytes(bytes);
			writeFileToTarget(bytes);

            Runtime.getRuntime().exec(new String[] {"C:\\WINDOWS\\system32\\mspaint.exe", targetFile});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFileToTarget(byte[] bytes) throws Exception {
        try(OutputStream os = new FileOutputStream(targetFile)) {
            os.write(bytes);
        }
    }

}

