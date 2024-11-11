import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord{
    public static void main(String[] args) {
        String champion = "";
        int i = 1;

        while(!StdIn.isEmpty()){
            String contender = StdIn.readString();
            double p = (double) 1/i;

            if(StdRandom.bernoulli(p)){
                champion = contender;
            }
            ++i;
        }

        System.out.println(champion);
    }
}
