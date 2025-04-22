import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection con = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer c = session.createConsumer(session.createTopic("MyTopic"));
        con.start();
        Message msg = c.receive();
        if (msg instanceof TextMessage)
            System.out.println("Received: " + ((TextMessage) msg).getText());
        con.close();
    }
}
