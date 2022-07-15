public class Metal extends Material {
    Vector3 albedo;
    double fuzz;

    public Metal(Vector3 albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz < 1 ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vector3 attenuation, Ray scattered) {
        Vector3 reflected = Vector3.reflect(Vector3.unitVector(rIn.getDirection()), rec.normal);

        // scattered = new Ray(rec.p, reflected);
        // attenuation = albedo;

        scattered.origin = rec.p;
        scattered.direction = Vector3.addition(reflected, Vector3.multiplication(fuzz, Vector3.randomInUnitSphere()));

        attenuation.e[0] = albedo.getX();
        attenuation.e[1] = albedo.getY();
        attenuation.e[2] = albedo.getZ();

        return (Vector3.dot(scattered.getDirection(), rec.normal) > 0);
    }
}