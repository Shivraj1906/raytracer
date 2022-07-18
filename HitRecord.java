public class HitRecord {
    Vector3 p;
    Vector3 normal;
    Material material;
    double t;
    boolean frontFace;

    public HitRecord() {
        p = new Vector3();
        normal = new Vector3();
    }

    public void setFaceNormal(Ray r, Vector3 outwardNormal) {
        this.frontFace = Vector3.dot(r.getDirection(), outwardNormal) < 0;

        if (frontFace) {
            normal = outwardNormal;
        } else {
            normal = Vector3.multiplication(-1.0, outwardNormal);
        }
    }
}
