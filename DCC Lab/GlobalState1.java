import java.util.*;

class Message {
    int from, value;
    Message(int f, int v) { from = f; value = v; }
}

class Process {
    int id;
    int[] state = new int[3];
    boolean recording = false;
    Queue<Message> queue = new LinkedList<>();

    Process(int id) { this.id = id; }

    void receive(Message m) {
        if (recording) state[m.from] = m.value;
        else queue.add(m);
    }

    void start() { recording = true; }
    void stop() {
        recording = false;
        while (!queue.isEmpty()) {
            Message m = queue.poll();
            state[m.from] = m.value;
        }
    }

    void show() {
        System.out.println("P" + id + ": " + Arrays.toString(state));
    }
}

public class GlobalState1 {
    public static void main(String[] args) {
        Process[] p = { new Process(0), new Process(1), new Process(2) };

        p[0].start();                   // P0 starts recording
        p[0].receive(new Message(1, 5));
        
        p[1].start();                   // P1 starts recording
        p[1].receive(new Message(0, 3));
        p[1].stop();                    // P1 stops recording

        p[0].stop();                    // P0 stops recording

        for (Process x : p) x.show();
    }
}
