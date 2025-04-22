import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
public class HelloServer extends UnicastRemoteObject implements Hello {
 protected HelloServer() throws RemoteException {
 super();
 }
 public String sayHello() {
 return "Hello, world!";
 }
 public static void main(String args[]) {
 try {
 Hello stub = new HelloServer();
 Naming.rebind("rmi://localhost:5000/hello", stub);
 System.out.println("Server ready");
 } catch (Exception e) {
 System.err.println("Server exception: " + e.toString());
 e.printStackTrace();
 }
 }
}
