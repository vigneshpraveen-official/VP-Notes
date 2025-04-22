import java.rmi.*;

public interface Adder extends Remote {
    int add(int a, int b) throws RemoteException;
}
