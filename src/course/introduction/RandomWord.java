package course.introduction;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int counter = 1;
        String champion = null;

        while (!StdIn.isEmpty()) {
            String cur = StdIn.readString();

            double probability = 1.0 / counter;
            if (StdRandom.bernoulli(probability)) {
                champion = cur;
            }

            counter++;
        }

        StdOut.println(champion);
    }
}
