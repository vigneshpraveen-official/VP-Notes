import java.rmi.*;
import java.rmi.server.*;

public class AdderImpl extends UnicastRemoteObject implements Adder {
    AdderImpl() throws RemoteException {
        super();
    }

    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        try {
            Naming.rebind("rmi://localhost:2000/add", new AdderImpl());
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
