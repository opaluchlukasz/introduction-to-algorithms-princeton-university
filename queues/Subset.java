import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import static edu.princeton.cs.algs4.StdIn.readString;
import static java.lang.Integer.*;

public class Subset {
    public static void main(String[] args) {

        int k = parseInt(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        while(!StdIn.isEmpty()) {
            randomizedQueue.enqueue(readString());
        }

        for(int i = 0;  i < k; i++) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}