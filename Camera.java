public class Camera {
    Vector3 origin;
    Vector3 lowerLeftCorner;
    Vector3 horizontal;
    Vector3 vertical;
    Vector3 u, v, w;
    double lensRadius;

    public Camera(Vector3 lookFrom, Vector3 lookAt, Vector3 vup, double vfov, double aspectRatio, double aperture,
            double focusDistance) {
        double theta = Constants.degreeToRadians(vfov);
        double h = Math.tan(theta / 2);
        double viewportHeight = 2.0 * h;
        double viewportWidth = aspectRatio * viewportHeight;

        w = Vector3.unitVector(Vector3.subtraction(lookFrom, lookAt));
        u = Vector3.unitVector(Vector3.cross(vup, w));
        v = Vector3.cross(w, u);

        origin = lookFrom;
        horizontal = Vector3.multiplication(focusDistance, Vector3.multiplication(viewportWidth, u));
        vertical = Vector3.multiplication(focusDistance, Vector3.multiplication(viewportHeight, v));
        lowerLeftCorner = Vector3
                .subtraction(Vector3.subtraction(Vector3.subtraction(origin, Vector3.division(horizontal, 2)),
                        Vector3.division(vertical, 2)), Vector3.multiplication(focusDistance, w));

        lensRadius = aperture / 2;
    }

    Ray getRay(double s, double t) {
        Vector3 rd = Vector3.multiplication(lensRadius, Vector3.randomInUnitDisk());
        Vector3 offsetLeft = Vector3.multiplication(rd.getX(), u);
        Vector3 offsetRight = Vector3.multiplication(rd.getY(), v);

        Vector3 offset = Vector3.addition(offsetLeft, offsetRight);

        Vector3 tempS = Vector3.multiplication(s, horizontal);
        Vector3 tempT = Vector3.multiplication(t, vertical);

        return new Ray(
                Vector3.addition(origin, offset),
                Vector3.subtraction(
                        Vector3.subtraction(Vector3.addition(Vector3.addition(lowerLeftCorner, tempS), tempT), origin),
                        offset));
    }
}
