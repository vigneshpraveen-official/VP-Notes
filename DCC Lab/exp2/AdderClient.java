import java.rmi.*;

public class AdderClient {
    public static void main(String[] args) {
        try {
            Adder stub = (Adder) Naming.lookup("rmi://localhost:2000/add");
            System.out.println("Result: " + stub.add(10, 20));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
