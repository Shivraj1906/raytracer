import java.util.Random;

public class Constants {
    public static double infinity = Double.MAX_VALUE;
    public static double pi = Math.PI;

    static double degreeToRadians(double degrees) {
        return degrees * pi / 180.0;
    }

    static double randomDouble() {
        Random random = new Random();

        return random.nextDouble();
    }

    static double randomDouble(double min, double max) {
        Random random = new Random();

        return min + (max - min) * random.nextDouble();
    }

    static double clamp(double x, double min, double max) {
        if (x < min)
            return min;
        if (x > max)
            return max;

        return x;
    }
}
