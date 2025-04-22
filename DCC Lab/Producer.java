import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    public static void main(String[] args) throws Exception {
        Connection con = new ActiveMQConnectionFactory("tcp://localhost:61616").createConnection();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer p = session.createProducer(session.createTopic("MyTopic"));
        con.start();
        p.send(session.createTextMessage("Hello from Producer!"));
        System.out.println("Message Sent!");
        con.close();
    }
}
