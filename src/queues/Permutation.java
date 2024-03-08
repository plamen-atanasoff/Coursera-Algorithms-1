package queues;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
//        int k = 3;
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String cur = StdIn.readString();
            randomizedQueue.enqueue(cur);
        }

        int ctr = 0;
        for (String s : randomizedQueue) {
            if (ctr == k) {
                break;
            }

            System.out.println(s);

            ctr++;
        }
    }
}
