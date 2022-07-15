// P(t) = A + tb
//
// Where A is the origin of the ray and b is the
// direction of the ray
// t is the real number
// plugin diffrent t values to move along the ray

public class Ray {

    Vector3 origin;
    Vector3 direction;

    public Ray() {
        origin = new Vector3();
        direction = new Vector3();
    }

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    Vector3 at(double t) {
        return Vector3.addition(origin, Vector3.multiplication(t, direction));
    }
}
