public class Lamport {
    public static void main(String[] args) {
        int[] p1 = {1, 2, 3, 4, 5};
        int[] p2 = {1, 2, 3};
        int[][] msg = {
            {0, 0, 0},
            {0, 0, 1},
            {0, 0, 0},
            {0, 0, 0},
            {0, -1, 0}
        };

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (msg[i][j] == 1) {
                    p2[j] = Math.max(p2[j], p1[i] + 1);
                    for (int k = j + 1; k < 3; k++)
                        p2[k] = p2[k - 1] + 1;
                }
                if (msg[i][j] == -1) {
                    p1[i] = Math.max(p1[i], p2[j] + 1);
                    for (int k = i + 1; k < 5; k++)
                        p1[k] = p1[k - 1] + 1;
                }
            }
        }

        System.out.print("P1: ");
        for (int t : p1) System.out.print(t + " ");
        System.out.print("\nP2: ");
        for (int t : p2) System.out.print(t + " ");
    }
}
