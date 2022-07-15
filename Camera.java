public class Camera {
    Vector3 origin;
    Vector3 lowerLeftCorner;
    Vector3 horizontal;
    Vector3 vertical;

    public Camera() {
        double aspectRatio = 16.0 / 9.0;
        double viewportHeight = 2.0;
        double viewportWidth = aspectRatio * viewportHeight;
        double focalLength = 1.0;

        origin = new Vector3(0, 0, 0);
        horizontal = new Vector3(viewportWidth, 0, 0);
        vertical = new Vector3(0, viewportHeight, 0);
        lowerLeftCorner = Vector3
                .subtraction(Vector3.subtraction(Vector3.subtraction(origin, Vector3.division(horizontal, 2)),
                        Vector3.division(vertical, 2)), new Vector3(0, 0, focalLength));
    }

    Ray getRay(double u, double v) {
        return new Ray(origin,
                Vector3.subtraction(Vector3.addition(
                        Vector3.addition(lowerLeftCorner, Vector3.multiplication(u, horizontal)),
                        Vector3.multiplication(v, vertical)), origin));
    }
}
