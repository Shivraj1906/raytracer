public class Sphere extends Hittable {

    Vector3 center;
    double radius;
    Material material;

    public Sphere(Vector3 center, double r, Material material) {
        this.center = center;
        this.radius = r;
        this.material = material;
    }

    public boolean hit(Ray r, double tMin, double tMax, HitRecord rec) {
        Vector3 oc = Vector3.subtraction(r.getOrigin(), center);
        double a = r.getDirection().lengthSquared();
        double half_b = Vector3.dot(oc, r.getDirection());
        double c = oc.lengthSquared() - radius * radius;

        double discriminant = half_b * half_b - a * c;

        if (discriminant < 0) {
            return false;
        }

        double sqrt = Math.sqrt(discriminant);

        double root = (-half_b - sqrt) / a;
        if (root < tMin || tMax < root) {
            root = (-half_b + sqrt) / a;
            if (root < tMin || tMax < root) {
                return false;
            }
        }

        rec.t = root;
        rec.p = r.at(rec.t);
        Vector3 outwardNormal = Vector3.division(Vector3.subtraction(rec.p, center), radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.material = this.material;

        return true;
    }
}
