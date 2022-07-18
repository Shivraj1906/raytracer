public class Vector3 {
    public double e[];

    public Vector3() {
        e = new double[3];
    }

    public Vector3(double e1, double e2, double e3) {
        e = new double[3];

        e[0] = e1;
        e[1] = e2;
        e[2] = e3;
    }

    public Vector3(Vector3 v) {
        e[0] = v.getX();
        e[1] = v.getY();
        e[2] = v.getZ();
    }

    double getX() {
        return e[0];
    }

    double getY() {
        return e[1];
    }

    double getZ() {
        return e[2];
    }

    double length() {
        return Math.sqrt(lengthSquared());
    }

    double lengthSquared() {
        return e[0] * e[0] + e[1] * e[1] + e[2] * e[2];
    }

    static void out(Vector3 v) {
        System.out.print(v.e[0] + " " + v.e[1] + " " + v.e[2]);
    }

    static Vector3 addition(Vector3 u, Vector3 v) {
        return new Vector3(u.e[0] + v.e[0], u.e[1] + v.e[1], u.e[2] + v.e[2]);
    }

    static Vector3 subtraction(Vector3 u, Vector3 v) {
        return new Vector3(u.e[0] - v.e[0], u.e[1] - v.e[1], u.e[2] - v.e[2]);
    }

    static Vector3 multiplication(Vector3 u, Vector3 v) {
        return new Vector3(u.e[0] * v.e[0], u.e[1] * v.e[1], u.e[2] * v.e[2]);
    }

    static Vector3 multiplication(double t, Vector3 v) {
        return new Vector3(t * v.e[0], t * v.e[1], t * v.e[2]);
    }

    static Vector3 multiplicaation(Vector3 v, double t) {
        return multiplication(t, v);
    }

    static Vector3 division(Vector3 v, double t) {
        return multiplication(1 / t, v);
    }

    static double dot(Vector3 u, Vector3 v) {
        return u.e[0] * v.e[0]
                + u.e[1] * v.e[1]
                + u.e[2] * v.e[2];
    }

    static Vector3 cross(Vector3 u, Vector3 v) {
        return new Vector3(u.e[1] * v.e[2] - u.e[2] * v.e[1],
                u.e[2] * v.e[0] - u.e[0] * v.e[2],
                u.e[0] * v.e[1] - u.e[1] * v.e[0]);
    }

    static Vector3 unitVector(Vector3 v) {
        return division(v, v.length());
    }

    static Vector3 random() {
        return new Vector3(Constants.randomDouble(), Constants.randomDouble(), Constants.randomDouble());
    }

    static Vector3 random(double min, double max) {
        return new Vector3(Constants.randomDouble(min, max), Constants.randomDouble(min, max),
                Constants.randomDouble(min, max));
    }

    static Vector3 randomInUnitSphere() {
        while (true) {
            Vector3 p = random(-1, 1);

            if (p.lengthSquared() >= 1)
                continue;

            return p;
        }
    }

    static Vector3 randomUnitVector() {
        return Vector3.unitVector(randomInUnitSphere());
    }

    boolean nearZero() {
        double s = 1e-8;

        return (Math.abs(e[0]) < s) && (Math.abs(e[1]) < s) && (Math.abs(e[2]) < s);
    }

    static Vector3 reflect(Vector3 v, Vector3 n) {
        Vector3 answer = Vector3.subtraction(v, Vector3.multiplication(2 * Vector3.dot(v, n), n));
        return answer;
    }

    static Vector3 refract(Vector3 uv, Vector3 n, double etaiOverEtat) {
        double cosTheta = Math.min(Vector3.dot(Vector3.multiplication(-1.0, uv), n), 1.0);
        Vector3 rOutPerp = Vector3.multiplication(etaiOverEtat,
                Vector3.addition(uv, Vector3.multiplication(cosTheta, n)));
        Vector3 rOutParallel = Vector3.multiplication(-1.0 * Math.sqrt(Math.abs(1.0 - rOutPerp.lengthSquared())), n);

        return Vector3.addition(rOutPerp, rOutParallel);
    }

    static Vector3 randomInUnitDisk() {
        while (true) {
            Vector3 p = new Vector3(Constants.randomDouble(-1, 1), Constants.randomDouble(-1, 1), 0.0);
            if (p.lengthSquared() >= 1)
                continue;

            return p;

        }
    }

    /////////// -------Color util functions-----------/////////

    static public void writeColor(Vector3 v, int samplesPerPixel) {

        double r = v.getX();
        double g = v.getY();
        double b = v.getZ();

        // divide the colors to samples
        double scale = 1.0 / samplesPerPixel;

        r = Math.sqrt(scale * r);
        g = Math.sqrt(scale * g);
        b = Math.sqrt(scale * b);

        System.out.print(
                (int) (256 * Constants.clamp(r, 0.0, 0.999)) + " " +
                        (int) (256 * Constants.clamp(g, 0.0, 0.999)) + " " +
                        (int) (256 * Constants.clamp(b, 0.0, 0.999)) + "\n");
    }
}