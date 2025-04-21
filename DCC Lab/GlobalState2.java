import java.util.*;

public class GlobalState2 {
    static int[][] clocks = new int[3][3];
    static String[] state = new String[3];
    static boolean[] markerReceived = new boolean[3];

    public static void main(String[] args) {
        // Initialize states
        state[0] = "P0: running";
        state[1] = "P1: idle";
        state[2] = "P2: computing";

        // Simulate sending some messages
        sendMessage(0, 1);
        sendMessage(1, 2);
        sendMessage(2, 0);

        // Initiate snapshot at P0
        recordSnapshot(0);
    }

    static void sendMessage(int from, int to) {
        clocks[from][from]++;
        System.out.println("Message sent from P" + from + " to P" + to + " | Clock: " + Arrays.toString(clocks[from]));
    }

    static void recordSnapshot(int initiator) {
        System.out.println("\nRecording snapshot initiated by P" + initiator);
        markerReceived[initiator] = true;

        for (int i = 0; i < 3; i++) {
            if (!markerReceived[i]) {
                markerReceived[i] = true;
                System.out.println("Marker received at P" + i);
            }
            System.out.println("P" + i + " State: " + state[i]);
            System.out.println("P" + i + " Clock: " + Arrays.toString(clocks[i]));
        }
    }
}
