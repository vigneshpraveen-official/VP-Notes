class SimpleProcess extends Thread {
    int id;
    SimpleProcess other;
    boolean hasToken = false;
    boolean requested = false;

    SimpleProcess(int id) {
        this.id = id;
    }

    public void run() {
        try {
            if (!hasToken) {
                System.out.println("Process " + id + " requesting token...");
                requested = true;
                while (!hasToken) {
                    Thread.sleep(100); // wait until token is received
                }
            }

            // Critical section
            System.out.println("Process " + id + " ENTERED Critical Section");
            Thread.sleep(1000);
            System.out.println("Process " + id + " EXITED Critical Section");

            // Pass token to the other process
            hasToken = false;
            if (other.requested) {
                other.hasToken = true;
                other.requested = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Maekawa {
    public static void main(String[] args) {
        SimpleProcess p1 = new SimpleProcess(1);
        SimpleProcess p2 = new SimpleProcess(2);

        // Setup
        p1.other = p2;
        p2.other = p1;

        // Initially give token to p1
        p1.hasToken = true;

        // Start both
        p1.start();
        p2.start();
    }
}
