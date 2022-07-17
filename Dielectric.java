public class Dielectric extends Material {
    double ir;

    public Dielectric(double indexOfRefraction) {
        this.ir = indexOfRefraction;
    }

    static double reflectance(double cosine, double refIndex) {
        double r0 = (1 - refIndex) / (1 + refIndex);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow(1 - cosine, 5);
    }

    @Override
    public boolean scatter(Ray rIn, HitRecord rec, Vector3 attenuation, Ray scattered) {
        attenuation.e[0] = 1;
        attenuation.e[1] = 1;
        attenuation.e[2] = 1;

        double refractionRatio = rec.frontFace ? (1.0 / ir) : ir;

        Vector3 unitDirection = Vector3.unitVector(rIn.getDirection());

        double cosTheta = Math.min(Vector3.dot(Vector3.multiplication(-1.0, unitDirection), rec.normal), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0;
        Vector3 direction;

        if (cannotRefract || reflectance(cosTheta, refractionRatio) > Constants.randomDouble())
            direction = Vector3.reflect(unitDirection, rec.normal);
        else
            direction = Vector3.refract(unitDirection, rec.normal, refractionRatio);

        scattered.origin = rec.p;
        scattered.direction = direction;

        return true;
    }
}
