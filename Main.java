import java.util.Timer;

public class Main {

    static Vector3 rayColor(Ray r, Hittable world, int depth) {
        HitRecord rec = new HitRecord();

        if (depth <= 0)
            return new Vector3(0, 0, 0);

        if (world.hit(r, 0.001, Constants.infinity, rec)) {
            Ray scattered = new Ray();
            Vector3 attenuation = new Vector3();

            if (rec.material.scatter(r, rec, attenuation, scattered)) {
                return Vector3.multiplication(attenuation, rayColor(scattered, world, depth - 1));
            }

            return new Vector3(0, 0, 0);
        }
        Vector3 unitDirection = Vector3.unitVector(r.getDirection());
        double t = 0.5 * (unitDirection.getY() + 1.0);
        return Vector3.addition(Vector3.multiplication(1.0 - t, new Vector3(1.0, 1.0, 1.0)),
                Vector3.multiplication(t, new Vector3(0.5, 0.7, 1.0)));
    }

    public static void main(String[] args) {

        // Image
        double aspectRatio = 16.0 / 9.0;
        int width = 420;
        int height = (int) (width / aspectRatio);
        int samplePerPixel = 10;
        int maxDepth = 8;

        // World
        HittableList world = new HittableList();

        Lambertian materialGround = new Lambertian(new Vector3(0.8, 0.8, 0));
        Lambertian materialCenter = new Lambertian(new Vector3(0.8, 0.8, 0.7));
        Dielectric materialLeft = new Dielectric(1.5);
        Metal materialRight = new Metal(new Vector3(0.8, 0.6, 0.2), 0.0);

        world.add(new Sphere(new Vector3(0, -100.5, -1.0), 100.0, materialGround));
        world.add(new Sphere(new Vector3(0, 0, -1.0), 0.5, materialCenter));
        world.add(new Sphere(new Vector3(-1.0, 0, -1.0), -0.5, materialLeft));
        world.add(new Sphere(new Vector3(1.0, 0, -1.0), 0.5, materialRight));

        // Camera

        Camera camera = new Camera();

        // Render

        System.out.print("P3\n" + width + " " + height + "\n255\n");

        Timer timer = new Timer();
        TimerHelper helper = new TimerHelper();

        timer.schedule(helper, 1, 1);

        for (int j = height - 1; j >= 0; j--) {
            for (int i = 0; i < width; i++) {
                Vector3 pixelColor = new Vector3(0, 0, 0);
                for (int s = 0; s < samplePerPixel; s++) {
                    double u = (i + Constants.randomDouble()) / (width - 1);
                    double v = (j + Constants.randomDouble()) / (height - 1);

                    Ray r = camera.getRay(u, v);
                    pixelColor = Vector3.addition(pixelColor, rayColor(r, world, maxDepth));
                }
                Vector3.writeColor(pixelColor, samplePerPixel);

            }
        }
        // System.err.println("Done.");
        System.err.println(TimerHelper.ms + " ms");
        timer.cancel();
    }
}