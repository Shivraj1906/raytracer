abstract public class Material {
    public abstract boolean scatter(Ray rIn, HitRecord rec, Vector3 attenuation, Ray scattered);
}
