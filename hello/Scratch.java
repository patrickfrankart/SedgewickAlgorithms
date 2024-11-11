import java.util.stream.IntStream;

public class Scratch {
    public static void main(String[] args) {
        System.out.println(calculateEndingIRAAmount(100000.00, .10, 30));
    }

    public static double calculateEndingIRAAmount(double startingAmount, double interestRate, int duration){
        return IntStream.range(0, duration).mapToDouble(a -> 7000.00).reduce(startingAmount, (a, b) -> (1.0 + interestRate) * a + b);
    }
}
