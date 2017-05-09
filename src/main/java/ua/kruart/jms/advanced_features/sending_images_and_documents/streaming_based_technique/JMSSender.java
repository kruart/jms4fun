package ua.kruart.jms.advanced_features.sending_images_and_documents.streaming_based_technique;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

public class JMSSender {

	private Connection connection;
	private Session session;
	private MessageProducer sender;
	private String uuid;

	private static String sourceFile = "D:\\projects\\java\\jms\\jms_4fun\\images\\juve_newLogo.jpg";
	private static String tempDir = "D:\\projects\\java\\jms\\jms_4fun\\images\\tempImageDir\\";

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
    	uuid = UUID.randomUUID().toString();

    	sendStream(null, "START");    	
    	
    	InputStream is = new FileInputStream(sourceFile);
    	byte[] bytes = new byte[10000000];
		while ((is.read(bytes)) >= 0) {
			sendStream(bytes, null);
		}
		
		sendStream(null, "END");
		is.close();

		System.out.println("Image send complete");
		connection.close();
		System.exit(0);
    }

    public void sendStream(byte[] bytes, String marker) throws Exception {
		BytesMessage msg = session.createBytesMessage();
		msg.setStringProperty("JMSXGroupID", uuid);
		if (bytes == null) {
			msg.setStringProperty("sequenceMarker", marker);
			System.out.println("Sending " + marker);
		} else {
			msg.writeBytes(bytes);
			System.out.println("Streaming...");
		}
		sender.send(msg);
	}
}


/**
 *     public void sendMessage() throws Exception {
    	
    	String msgFile = writeFileToTemp(readFileFromSource());
    	TextMessage msg = session.createTextMessage(msgFile);
    	sender.send(msg);
		System.out.println("Message Sent");
		
		BytesMessage msg = session.createBytesMessage();
		msg.writeBytes(readFileFromSource());
		sender.send(msg);
		System.out.println("Message Sent");
		
		InputStream is = new FileInputStream(sourceFile);
		uuid = UUID.randomUUID().toString();
		sendImage(null, "START");
		byte[] bytes = new byte[10000000];
		while ((is.read(bytes)) >= 0) {
			sendImage(bytes, null);
		}
		sendImage(null, "END");
		is.close();
		connection.close();
		
    	//streamread
		InputStream is = new FileInputStream(sourceFile);
		//streamuuid (used for later)
		uuid = UUID.randomUUID().toString();

		//streamstart
		sendImage(null, "START"); 

		
		//streamwrite
		byte[] bytes = new byte[10000000]; //200000 (used for pint when demonstrating multiple consumers)
        while ((is.read(bytes)) >= 0) {
        	sendImage(bytes, null);
        }

        //streamend
        sendImage(null, "END");    	
		is.close();
		connection.close();
		System.exit(0);
	}

    public void sendImage(byte[] bytes, String marker) throws Exception {
		BytesMessage msg = session.createBytesMessage();
		if (bytes == null) {
			msg.setStringProperty("sequenceMarker", marker);
			System.out.println("Sending " + marker);
		} else {
			msg.writeBytes(bytes);
			System.out.println("Streaming...");
		}
		sender.send(msg);
	}
    
    //streamsend
    public void sendImage(byte[] bytes, String marker) throws Exception {
        BytesMessage msg = session.createBytesMessage();
        //streamgroupid
        msg.setStringProperty("JMSXGroupID", uuid);
        msg.setStringProperty("JMSXGroupID", uuid);
        if (bytes == null) {
    		msg.setStringProperty("sequenceMarker", marker);
    		System.out.println("Sending " + marker);
        } else {
            msg.writeBytes(bytes);
            System.out.println("Streaming...");
        }
		sender.send(msg);
    }

    public byte[] readFileFromSource() throws Exception {
		File file = new File(sourceFile);
		try (InputStream is = new FileInputStream(file);) {
			byte[] bytes = new byte[(int) file.length()];
			is.read(bytes);
			return bytes;
		}
	}
    
    filewrite
    public byte[] readFileFromSource() throws Exception {
		File file = new File(sourceFile);
		try (InputStream is = new FileInputStream(file);) {
			byte[] bytes = new byte[(int)file.length()];
	        is.read(bytes);
	        return bytes;
		}
    }
    
	public String writeFileToTemp(byte[] bytes) throws Exception {
        String filename = tempDir + UUID.randomUUID();
		try (OutputStream os = new FileOutputStream(filename);) {
	    	os.write(bytes);
	    	return filename;
		}
	}


 * 
*/
