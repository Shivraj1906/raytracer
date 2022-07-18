import java.util.Vector;

public class HittableList extends Hittable {
    public Vector<Hittable> objects;

    HittableList() {
        objects = new Vector<>();
    }

    HittableList(Hittable object) {
        objects = new Vector<>();
        objects.add(object);
    }

    public void clear() {
        objects.clear();
    }

    public void add(Hittable object) {
        objects.add(object);
    }

    public boolean hit(Ray r, double tMin, double tMax, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;

        double closestSoFar = tMax;

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).hit(r, tMin, closestSoFar, tempRec)) {
                hitAnything = true;
                closestSoFar = tempRec.t;
                // rec = tempRec;
                rec.p = tempRec.p;
                rec.normal = tempRec.normal;
                rec.t = tempRec.t;
                rec.material = tempRec.material;
                rec.frontFace = tempRec.frontFace;
            }
        }

        return hitAnything;
    }
}
