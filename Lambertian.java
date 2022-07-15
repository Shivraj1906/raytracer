public class Lambertian extends Material {
    Vector3 albedo;

    public Lambertian(Vector3 albedo) {
        this.albedo = albedo;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vector3 attenuation, Ray scattered) {
        Vector3 scatterDirection = Vector3.addition(rec.normal, Vector3.randomUnitVector());

        if (scatterDirection.nearZero()) {
            scatterDirection = rec.normal; // TODO this too
        }

        // scattered = new Ray(rec.p, scatterDirection);
        scattered.origin = rec.p;
        scattered.direction = scatterDirection;
        attenuation.e[0] = albedo.getX(); // TODO this line might create problems
        attenuation.e[1] = albedo.getY();
        attenuation.e[2] = albedo.getZ();
        return true;
    }

}
