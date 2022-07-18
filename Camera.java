public class Camera {
    Vector3 origin;
    Vector3 lowerLeftCorner;
    Vector3 horizontal;
    Vector3 vertical;

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 vup, double vfov, double aspectRatio) {
        double theta = Constants.degreeToRadians(vfov);
        double h = Math.tan(theta / 2);
        double viewportHeight = 2.0 * h;
        double viewportWidth = aspectRatio * viewportHeight;

        Vector3 w = Vector3.unitVector(Vector3.subtraction(lookFrom, lookAt));
        Vector3 u = Vector3.unitVector(Vector3.cross(vup, w));
        Vector3 v = Vector3.cross(w, u);

        origin = lookFrom;
        horizontal = Vector3.multiplication(viewportWidth, u);
        vertical = Vector3.multiplication(viewportHeight, v);
        lowerLeftCorner = Vector3
                .subtraction(Vector3.subtraction(Vector3.subtraction(origin, Vector3.division(horizontal, 2)),
                        Vector3.division(vertical, 2)), w);
    }

    Ray getRay(double s, double t) {
        return new Ray(origin,
                Vector3.subtraction(Vector3.addition(
                        Vector3.addition(lowerLeftCorner, Vector3.multiplication(s, horizontal)),
                        Vector3.multiplication(t, vertical)), origin));
    }
}
