package ua.kruart.jms.advanced_features.sending_images_and_documents.streaming_based_technique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class JMSReceiver implements MessageListener {

	private static String targetFile = "D:\\projects\\java\\jms\\jms_4fun\\images\\targetDir\\juve.jpg";

	private OutputStream os;

	public JMSReceiver() {
		try {
			Connection connection = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("EM_IMAGE.Q");
			MessageConsumer receiver = session.createConsumer(queue);
			receiver.setMessageListener(this);
			os = new FileOutputStream(targetFile);
			System.out.println("Waiting for messages...");
		} catch (Exception up) {
			up.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		new Thread(JMSReceiver::new).start();
	}

	public void onMessage(Message message) {
		try {

			if (message.propertyExists("sequenceMarker")) {
				String marker = message.getStringProperty("sequenceMarker");
				System.out.println("Received Marker: " + marker);

				if (marker.equals("START")) {
					os = new FileOutputStream(targetFile);
				}

				if (marker.equals("END")) {
					os.close();
					Runtime.getRuntime().exec(new String[] {"C:\\WINDOWS\\system32\\mspaint.exe", targetFile});
				}
			} else {
				BytesMessage msg = (BytesMessage) message;
				System.out.println("Received Message");
				byte[] bytes = new byte[new Long(msg.getBodyLength()).intValue()];
				msg.readBytes(bytes);
				os.write(bytes);
			}

		} catch (Exception up) {
			up.printStackTrace();
		}

	}

}











